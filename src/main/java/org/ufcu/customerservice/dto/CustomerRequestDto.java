package org.ufcu.customerservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerRequestDto {
    
    @NotBlank(message = "firstName is required and cannot be null or empty")
    private String firstName;
    
    @NotBlank(message = "lastName is required and cannot be null or empty")
    private String lastName;
    
    @Email(message = "email should be valid")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "phone is required and cannot be null or empty")
    private String phone;
    
    @NotNull(message = "dateOfBirth is required and cannot be null")
    private LocalDate dateOfBirth;
    
    @NotBlank(message = "country is required and cannot be null or empty")
    private String country;
    
    @Valid
    private AddressRequestDto address;
}