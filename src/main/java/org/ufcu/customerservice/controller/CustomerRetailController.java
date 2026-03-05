package org.ufcu.customerservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.ufcu.customerservice.dto.CustomerRetailRequest;
import org.ufcu.customerservice.dto.CustomerRetailResponse;
import org.ufcu.customerservice.service.CustomerRetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer-retail")
@Validated
public class CustomerRetailController {

    @Autowired
    private CustomerRetailService customerRetailService;

    @PostMapping("/{caseId}")
    public ResponseEntity<CustomerRetailResponse> createCustomerRetail(
            @PathVariable @NotBlank(message = "caseId is required and cannot be null or empty") String caseId,
            @RequestBody @Valid CustomerRetailRequest request) {
        CustomerRetailResponse response = customerRetailService.createCustomerRetail(caseId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}


