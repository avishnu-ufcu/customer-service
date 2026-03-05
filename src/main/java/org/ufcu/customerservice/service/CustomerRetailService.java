package org.ufcu.customerservice.service;

import org.ufcu.customerservice.domain.Address;
import org.ufcu.customerservice.domain.CustomerRetail;
import org.ufcu.customerservice.domain.PartyIdentification;
import org.ufcu.customerservice.dto.CustomerRetailRequest;
import org.ufcu.customerservice.dto.CustomerRetailResponse;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class CustomerRetailService {

    public CustomerRetailResponse createCustomerRetail(String caseId, CustomerRetailRequest request) {
        // Generate customerId
        String customerId = generateCustomerId();

        // Map request to domain object
        CustomerRetail customerRetail = mapRequestToDomain(request, customerId);

        // TODO: Save to database if needed
        // For now, we're just returning the response based on the created domain object

        // Map domain object to response payload
        return mapDomainToResponse(customerRetail, caseId);
    }

    private String generateCustomerId() {
        return "CUST-" + String.format("%08d", UUID.randomUUID().hashCode() & 0xFFFFFFFF).substring(0, 8);
    }

    private CustomerRetail mapRequestToDomain(CustomerRetailRequest request, String customerId) {
        CustomerRetail customerRetail = new CustomerRetail();
        customerRetail.setCustomerId(customerId);
        customerRetail.setFirstName(request.getFirstName());
        customerRetail.setLastName(request.getLastName());
        customerRetail.setDateOfBirth(request.getDateOfBirth());
        // Map nested PartyIdentification
        if (request.getPartyIdentification() != null) {
            PartyIdentification partyId = new PartyIdentification(
                request.getPartyIdentification().getPartyIdentificationType(),
                request.getPartyIdentification().getPartyIdentificationValue()
            );
            customerRetail.setPartyIdentification(partyId);
        }

        customerRetail.setEmail(request.getEmail());
        customerRetail.setPhone(request.getPhone());

        // Map nested Address
        if (request.getAddress() != null) {
            Address address = new Address(
                request.getAddress().getLine1(),
                request.getAddress().getLine2(),
                request.getAddress().getCity(),
                request.getAddress().getState(),
                request.getAddress().getPostalCode(),
                request.getAddress().getCountry()
            );
            customerRetail.setAddress(address);
        }

        customerRetail.setStatus("CUSTOMER_CREATED");

        return customerRetail;
    }

    private CustomerRetailResponse mapDomainToResponse(CustomerRetail domain, String caseId) {
        CustomerRetailResponse response = new CustomerRetailResponse();
        response.setCaseId(caseId);
        response.setCustomerId(domain.getCustomerId());
        response.setStatus("CUSTOMER_CREATED");
        response.setAction("CREATED");
        response.setCreatedAt(ZonedDateTime.now());

        return response;
    }
}

