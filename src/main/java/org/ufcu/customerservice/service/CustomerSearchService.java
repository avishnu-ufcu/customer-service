package org.ufcu.customerservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ufcu.customerservice.domain.Customer;
import org.ufcu.customerservice.dto.CitizenshipRequestDto;
import org.ufcu.customerservice.dto.CustomerRequestDto;
import org.ufcu.customerservice.dto.PassportRequestDto;
import org.ufcu.customerservice.repository.CustomerSearchRepository;

@Service
@Transactional(readOnly = true)
public class CustomerSearchService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerSearchService.class);

    @Autowired
    private CustomerSearchRepository customerSearchRepository;

    @Transactional(readOnly = true)
    public Customer searchCustomer(CustomerRequestDto customerDto, CitizenshipRequestDto citizenshipDto) {
        logger.debug("Searching for existing customer");
        boolean isUsCitizen = citizenshipDto != null && "us_citizen".equalsIgnoreCase(citizenshipDto.getStatus());

        if (isUsCitizen && citizenshipDto.getSsnOrItin() != null) {
            logger.debug("Searching US citizen by SSN/ITIN: {}", citizenshipDto.getSsnOrItin());
            Optional<Customer> customerOpt = customerSearchRepository.findBySsnOrItin(citizenshipDto.getSsnOrItin());
            if (customerOpt.isPresent()) {
                logger.info("Found existing customer by SSN/ITIN");
                return customerOpt.get();
            }
            logger.debug("No customer found by SSN/ITIN");
            return null;
        } else if (!isUsCitizen && citizenshipDto != null && citizenshipDto.getPassport() != null) {
            PassportRequestDto passportDto = citizenshipDto.getPassport();
            logger.debug("Searching non-US citizen by passport - Number: {}, Country: {}",
                        passportDto.getNumber(), passportDto.getCountry());
            Optional<Customer> customerOpt = customerSearchRepository.findByPassportNumberAndCountry(
                    passportDto.getNumber(), passportDto.getCountry());
            if (customerOpt.isPresent()) {
                logger.info("Found existing customer by passport");
                return customerOpt.get();
            }

            if (customerDto != null && customerDto.getFirstName() != null && customerDto.getLastName() != null && customerDto.getDateOfBirth() != null) {
                logger.debug("Searching by full name and passport - Name: {} {}, DOB: {}",
                            customerDto.getFirstName(), customerDto.getLastName(), customerDto.getDateOfBirth());
                Optional<Customer> fallbackOpt = customerSearchRepository.findByFullNameDobAndPassport(
                        customerDto.getFirstName(), customerDto.getLastName(), customerDto.getDateOfBirth(), passportDto.getNumber());
                if (fallbackOpt.isPresent()) {
                    logger.info("Found existing customer by full name and passport");
                    return fallbackOpt.get();
                }
            }
            logger.debug("No customer found by passport or name");
        }
        logger.debug("Customer search completed - no existing customer found");
        return null;
    }
}
