package org.ufcu.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OnboardingRequest {
    
    @NotBlank(message = "caseId is required and cannot be null or empty")
    @Size(min = 5, max = 100, message = "caseId must be between 5 and 100 characters")
    private String caseId;
    
    @NotNull(message = "customer information is required")
    @Valid
    private CustomerRequestDto customer;
    
    @NotNull(message = "citizenship information is required")
    @Valid
    private CitizenshipRequestDto citizenship;
    
    @Valid
    private List<@Valid ProfileRequestDto> additionalProfiles;
}
