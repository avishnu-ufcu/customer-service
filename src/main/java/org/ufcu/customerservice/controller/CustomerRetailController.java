package org.ufcu.customerservice.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ufcu.customerservice.dto.OnboardingRequest;
import org.ufcu.customerservice.dto.OnboardingResponse;
import org.ufcu.customerservice.service.OnboardingService;

@RestController
@RequestMapping("/api/customer-retail")
@Validated
@Slf4j
public class CustomerRetailController {

    @Autowired
    private OnboardingService onboardingService;

    @PostMapping
    public ResponseEntity<OnboardingResponse> createCustomerRetail(
            @Valid @RequestBody OnboardingRequest request) {

        log.info("Received onboarding request for case: {}", request.getCaseId());
        log.debug("Request - Customer Email: {}, Citizenship Status: {}",
                    request.getCustomer().getEmail(), request.getCitizenship().getStatus());

        try {
            OnboardingResponse response = onboardingService.createOnboarding(request);

            if ("EXISTS".equalsIgnoreCase(response.getStatus())) {
                log.info("Customer already exists - Case: {}", request.getCaseId());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            log.info("Customer onboarded successfully - Case: {}, CustomerId: {}",
                       request.getCaseId(), response.getCustomerId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error processing onboarding request for case: {}", request.getCaseId(), e);
            throw e;
        }
    }
}


