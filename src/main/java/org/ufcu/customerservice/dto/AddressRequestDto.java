package org.ufcu.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressRequestDto {
    
    @NotBlank(message = "street is required and cannot be null or empty")
    private String street;
    
    @NotBlank(message = "city is required and cannot be null or empty")
    private String city;
    
    @NotBlank(message = "state is required and cannot be null or empty")
    private String state;
    
    @NotBlank(message = "zip/postalCode is required and cannot be null or empty")
    private String zip;
    
    private String country;
}
