package org.ufcu.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfileAddressRequestDto {
    
    @NotBlank(message = "type is required and cannot be null or empty")
    private String type;
    
    @NotBlank(message = "addressLine is required and cannot be null or empty")
    private String addressLine;
    
    @NotBlank(message = "city is required and cannot be null or empty")
    private String city;
    
    @NotBlank(message = "state is required and cannot be null or empty")
    private String state;
    
    @NotBlank(message = "postalCode is required and cannot be null or empty")
    private String postalCode;
    
    @NotBlank(message = "country is required and cannot be null or empty")
    private String country;
}
