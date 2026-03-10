package org.ufcu.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CitizenshipRequestDto {
    
    @NotBlank(message = "status is required and cannot be null or empty")
    @Size(min = 2, max = 50, message = "status must be between 2 and 50 characters")
    private String status; // "us_citizen", "non_us", etc.

    @Pattern(regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{4}$|^$", message = "ssnOrItin must be in format XXX-XX-XXXX or empty")
    private String ssnOrItin; // Optional for non-US citizens

    // Passport information (for non-US citizens)
    @Valid
    private PassportRequestDto passport;

    // Visa information (for non-US citizens)
    @Valid
    private VisaRequestDto visa;

    // Government IDs (for non-US citizens)
    @Valid
    private List<GovernmentIdRequestDto> otherGovernmentIds;
}
