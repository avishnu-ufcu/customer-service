package org.ufcu.customerservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ufcu.customerservice.domain.*;
import org.ufcu.customerservice.dto.*;
import org.ufcu.customerservice.repository.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OnboardingService {

   @Autowired
    private UidGeneratorService uidGeneratorService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CitizenshipRepository citizenshipRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private EmploymentProfileRepository employmentProfileRepository;

    @Autowired
    private ProfileAddressRepository profileAddressRepository;

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private VisaRepository visaRepository;

    @Autowired
    private GovernmentIdRepository governmentIdRepository;

    @Autowired
    private CustomerSearchService customerSearchService;

    /**
     * Create onboarding with customer, citizenship, and profiles
     * Handles transaction management for all database operations
     */
    @Transactional
    public OnboardingResponse createOnboarding(OnboardingRequest request) {
            log.info("Starting onboarding process for case: {}", request.getCaseId());
            CitizenshipRequestDto citizenshipDto = request.getCitizenship();
            CustomerRequestDto customerDto = request.getCustomer();

            if ("us_citizen".equalsIgnoreCase(citizenshipDto.getStatus())) {
                if (citizenshipDto.getSsnOrItin() == null || citizenshipDto.getSsnOrItin().isBlank()) {
                    log.info("SSN/ITIN missing for US citizen with caseId {} ", request.getCaseId());
                    throw new IllegalArgumentException("ssnOrItin is required and must be provided.");

                }
            }

            log.info("Searching for existing customer for caseId: {}", request.getCaseId());
            Customer existingCustomer = customerSearchService.searchCustomer(customerDto, citizenshipDto);
            if (existingCustomer != null) {
                log.info("Customer already exists with ID: {}", existingCustomer.getCustomerId());
                OnboardingResponse response = buildOnboardingResponse(request.getCaseId(), existingCustomer,
                        existingCustomer.getProfiles().stream()
                            .map(profile -> profile.getId().toString())
                            .collect(Collectors.toList()));
                response.setMessage("Customer already exists with UPID: " + existingCustomer.getCustomerId());
                response.setStatus("EXISTS");
                return response;
            }

            log.debug("Creating new customer record for caseId: {}", request.getCaseId());
            Customer customer = mapRequestToCustomer(request.getCustomer());
        // Generate UID before saving
        String uid = uidGeneratorService.generateUID();
        customer.setUid(uid);

        customer = customerRepository.save(customer);
        log.info("Generated UID: {}", uid);
            log.info("Customer created successfully with caseId, customerID: {}, {}", request.getCaseId(), customer.getId());

            log.info("Creating citizenship record for caseId: {}", request.getCaseId());
            Citizenship citizenship = mapRequestToCitizenship(request.getCitizenship(), customer);
            citizenshipRepository.save(citizenship);
            log.info("Citizenship record created successfully for caseId: {}", request.getCaseId());

            List<String> profileIds = new ArrayList<>();
            if (request.getAdditionalProfiles() != null && !request.getAdditionalProfiles().isEmpty()) {
                log.debug("Processing {} profiles", request.getAdditionalProfiles().size());
                for (ProfileRequestDto profileRequest : request.getAdditionalProfiles()) {
                    log.debug("Processing profile of type: {}", profileRequest.getProfileType());
                    Profile profile = mapRequestToProfile(profileRequest, customer);
                    profile = saveProfile(profile);
                    profileIds.add(profile.getId().toString());
                    log.debug("Profile saved with ID: {}", profile.getId());
                }
            }

            log.info("Onboarding completed successfully for case: {}", request.getCaseId());
            return buildOnboardingResponse(request.getCaseId(), customer, profileIds);

    }

    public OnboardingResponse getCustomerByUpid(String upid) {
        Customer customer = customerRepository.findByCustomerId(upid)
                .orElseThrow(() -> new RuntimeException("Customer not found for UPID: " + upid));

        List<String> profileIds = customer.getProfiles().stream()
                .map(p -> p.getId().toString())
                .collect(Collectors.toList());

        return buildOnboardingResponse(upid, customer, profileIds); // already exists
    }

    /**
     * Map CustomerRequestDto to Customer entity
     */
    private Customer mapRequestToCustomer(CustomerRequestDto requestDto) {
        log.debug("Mapping customer request to entity - Email: {}", requestDto.getEmail());
        Customer customer = new Customer();
        customer.setFirstName(requestDto.getFirstName());
        customer.setLastName(requestDto.getLastName());
        customer.setEmail(requestDto.getEmail());
        customer.setPhone(requestDto.getPhone());
        customer.setDateOfBirth(requestDto.getDateOfBirth());
        customer.setCountry(requestDto.getCountry());

        // Map address
        if (requestDto.getAddress() != null) {
            log.debug("Mapping customer address");
            CustomerAddress address = mapRequestToCustomerAddress(requestDto.getAddress());
            address.setCustomer(customer);
            customer.setAddresses(new ArrayList<>(Collections.singletonList(address)));
        }

        log.debug("Customer mapping completed");
        return customer;
    }

    /**
     * Map AddressRequestDto to CustomerAddress entity
     */
    private CustomerAddress mapRequestToCustomerAddress(AddressRequestDto addressDto) {
        CustomerAddress address = new CustomerAddress();
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setPostalCode(addressDto.getZip());
        address.setAddressType("RESIDENTIAL");
        return address;
    }

    /**
     * Map CitizenshipRequestDto to Citizenship entity
     */
    private Citizenship mapRequestToCitizenship(CitizenshipRequestDto requestDto, Customer customer) {
        log.debug("Mapping citizenship for customer ID: {}, Status: {}", customer.getId(), requestDto.getStatus());
        Citizenship citizenship = new Citizenship();
        citizenship.setStatus(requestDto.getStatus());
        citizenship.setSsnOrItin(requestDto.getSsnOrItin());
        citizenship.setCustomer(customer);

        // Map Passport if present (OneToOne relationship)
        if (requestDto.getPassport() != null) {
            log.debug("Checking for existing passport with number: {}", requestDto.getPassport().getNumber());
            Optional<Passport> existingPassport =
                    passportRepository.findByPassportNumber(requestDto.getPassport().getNumber());

            if (existingPassport.isPresent()) {
                log.error("Passport already exists for another customer: {}", requestDto.getPassport().getNumber());
                throw new RuntimeException("Passport already exists for another customer");
            }

            log.debug("Mapping passport data");
            Passport passport = mapRequestToPassport(requestDto.getPassport(), citizenship);
            citizenship.setPassports(passport);
        }

        // Map Visa if present (OneToOne relationship)
        if (requestDto.getVisa() != null) {
            log.debug("Mapping visa data - Type: {}", requestDto.getVisa().getType());
            Visa visa = mapRequestToVisa(requestDto.getVisa(), citizenship);
            citizenship.setVisas(visa);
        }

        // Map Government IDs if present (OneToMany relationship)
        if (requestDto.getOtherGovernmentIds() != null && !requestDto.getOtherGovernmentIds().isEmpty()) {
            log.debug("Processing {} government IDs", requestDto.getOtherGovernmentIds().size());
            List<GovernmentId> governmentIds = new ArrayList<>();
            for (GovernmentIdRequestDto govIdDto : requestDto.getOtherGovernmentIds()) {
                log.debug("Mapping government ID - Type: {}", govIdDto.getIdType());
                GovernmentId governmentId = mapRequestToGovernmentId(govIdDto, citizenship);
                governmentIds.add(governmentId);
            }
            citizenship.setGovernmentIds(governmentIds);
        }

        log.debug("Citizenship mapping completed");
        return citizenship;
    }

    /**
     * Map ProfileRequestDto to Profile entity (Student or Employment)
     */
    private Profile mapRequestToProfile(ProfileRequestDto profileDto, Customer customer) {
        log.debug("Mapping profile - Type: {}", profileDto.getProfileType());
        Profile profile;

        if ("student".equalsIgnoreCase(profileDto.getProfileType())) {
            log.debug("Creating student profile");
            profile = mapToStudentProfile(profileDto, customer);
        } else if ("employment".equalsIgnoreCase(profileDto.getProfileType())) {
            log.debug("Creating employment profile");
            profile = mapToEmploymentProfile(profileDto, customer);
        } else {
            log.error("Invalid profile type: {}", profileDto.getProfileType());
            throw new IllegalArgumentException("Invalid profile type: " + profileDto.getProfileType());
        }

        // Map profile addresses
        if (profileDto.getAddresses() != null && !profileDto.getAddresses().isEmpty()) {
            log.debug("Mapping {} profile addresses", profileDto.getAddresses().size());
            List<ProfileAddress> addresses = profileDto.getAddresses().stream()
                    .map(addrDto -> mapRequestToProfileAddress(addrDto, profile))
                    .collect(Collectors.toList());
            profile.setAddresses(addresses);
        }

        log.debug("Profile mapping completed");
        return profile;
    }

    /**
     * Map ProfileRequestDto to StudentProfile entity
     */
    private StudentProfile mapToStudentProfile(ProfileRequestDto profileDto, Customer customer) {
        log.debug("Mapping student profile - University: {}, StudentId: {}",
                    profileDto.getUniversityName(), profileDto.getStudentId());
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setProfileType("STUDENT");
        studentProfile.setCustomer(customer);
        studentProfile.setUniversityName(profileDto.getUniversityName());
        studentProfile.setStudentId(profileDto.getStudentId());
        studentProfile.setI20Number(profileDto.getI20Number());
        studentProfile.setExpectedGraduation(profileDto.getExpectedGraduation());
        return studentProfile;
    }

    /**
     * Map ProfileRequestDto to EmploymentProfile entity
     */
    private EmploymentProfile mapToEmploymentProfile(ProfileRequestDto profileDto, Customer customer) {
        log.debug("Mapping employment profile - Organization: {}", profileDto.getOrganizationName());
        EmploymentProfile employmentProfile = new EmploymentProfile();
        employmentProfile.setProfileType("EMPLOYMENT");
        employmentProfile.setCustomer(customer);
        employmentProfile.setOrganizationName(profileDto.getOrganizationName());
        employmentProfile.setH1bOrEadNumber(profileDto.getH1bOrEadNumber());
        employmentProfile.setEmploymentStart(profileDto.getEmploymentStart());
        return employmentProfile;
    }

    /**
     * Map ProfileAddressRequestDto to ProfileAddress entity
     */
    private ProfileAddress mapRequestToProfileAddress(ProfileAddressRequestDto addressDto, Profile profile) {
        log.debug("Mapping profile address - City: {}, State: {}", addressDto.getCity(), addressDto.getState());
        ProfileAddress profileAddress = new ProfileAddress();
        profileAddress.setType(addressDto.getType());
        profileAddress.setAddressLine(addressDto.getAddressLine());
        profileAddress.setCity(addressDto.getCity());
        profileAddress.setState(addressDto.getState());
        profileAddress.setPostalCode(addressDto.getPostalCode());
        profileAddress.setCountry(addressDto.getCountry());
        profileAddress.setProfile(profile);
        return profileAddress;
    }

    /**
     * Map PassportRequestDto to Passport entity
     */
    private Passport mapRequestToPassport(PassportRequestDto passportDto, Citizenship citizenship) {
        log.debug("Mapping passport - Number: {}, Country: {}", passportDto.getNumber(), passportDto.getCountry());
        Passport passport = new Passport();
        passport.setPassportNumber(passportDto.getNumber());
        passport.setCountry(passportDto.getCountry());
        passport.setIssuanceDate(passportDto.getIssuanceDate());
        passport.setExpirationDate(passportDto.getExpirationDate());
        passport.setCitizenship(citizenship);
        return passport;
    }

    /**
     * Map VisaRequestDto to Visa entity
     */
    private Visa mapRequestToVisa(VisaRequestDto visaDto, Citizenship citizenship) {
        log.debug("Mapping visa - Type: {}, Number: {}", visaDto.getType(), visaDto.getNumber());
        Visa visa = new Visa();
        visa.setVisaType(visaDto.getType());
        visa.setVisaNumber(visaDto.getNumber());
        visa.setIssueDate(visaDto.getIssueDate());
        visa.setExpiryDate(visaDto.getExpiryDate());
        visa.setCitizenship(citizenship);
        return visa;
    }

    /**
     * Map GovernmentIdRequestDto to GovernmentId entity
     */
    private GovernmentId mapRequestToGovernmentId(GovernmentIdRequestDto govIdDto, Citizenship citizenship) {
        log.debug("Mapping government ID - Type: {}, Value: {}", govIdDto.getIdType(), govIdDto.getIdValue());
        GovernmentId governmentId = new GovernmentId();
        governmentId.setIdType(govIdDto.getIdType());
        governmentId.setIdValue(govIdDto.getIdValue());
        governmentId.setCitizenship(citizenship);
        return governmentId;
    }

    /**
     * Save profile based on type
     */
    private Profile saveProfile(Profile profile) {
        log.debug("Saving profile of type: {}", profile.getProfileType());
        if (profile instanceof StudentProfile) {
            log.debug("Saving student profile");
            return studentProfileRepository.save((StudentProfile) profile);
        } else if (profile instanceof EmploymentProfile) {
            log.debug("Saving employment profile");
            return employmentProfileRepository.save((EmploymentProfile) profile);
        }
        log.error("Unknown profile type: {}", profile.getClass().getName());
        throw new IllegalArgumentException("Unknown profile type");
    }

    /**
     * Build OnboardingResponse
     */
    private OnboardingResponse buildOnboardingResponse(String caseId, Customer customer, List<String> profileIds) {
        log.debug("Building onboarding response for case: {}, Customer: {}", caseId, customer.getCustomerId());
        OnboardingResponse response = new OnboardingResponse();
        response.setCaseId(caseId);
        response.setCustomerId(customer.getCustomerId());
        response.setUid(customer.getUid());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setPhone(customer.getPhone());
        response.setStatus("SUCCESS");
        response.setMessage("Customer created successfully");
        response.setCreatedAt(ZonedDateTime.now());
        return response;
    }
}





