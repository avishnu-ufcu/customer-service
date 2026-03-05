package org.ufcu.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerRetailRequest {
    @NotBlank(message = "firstName is required and cannot be null or empty")
    private String firstName;

    @NotBlank(message = "lastName is required and cannot be null or empty")
    private String lastName;

    @NotNull(message = "dateOfBirth is required and cannot be null")
    private LocalDate dateOfBirth;

    @NotBlank(message = "ssn is required and cannot be null or empty")
    private String ssn;

    @Valid
    private PartyIdentificationDto partyIdentification;

    @NotBlank(message = "email is required and cannot be null or empty")
    private String email;

    @NotBlank(message = "phone is required and cannot be null or empty")
    private String phone;

    @Valid
    private AddressDto address;

    @NotBlank(message = "status is required and cannot be null or empty")
    private String status;
}


