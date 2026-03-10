package org.ufcu.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfileRequestDto {
    
    @NotBlank(message = "profileType is required and cannot be null or empty")
    private String profileType;
    
    // Student Profile Fields
    private String universityName;
    private String studentId;
    private String i20Number;
    private LocalDate expectedGraduation;
    
    // Employment Profile Fields
    private String organizationName;
    private String h1bOrEadNumber;
    private LocalDate employmentStart;
    
    @Valid
    private List<ProfileAddressRequestDto> addresses;
}
