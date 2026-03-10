package org.ufcu.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PassportRequestDto {

    @NotBlank(message = "passport number is required")
    @Size(min = 5, max = 100, message = "passport number must be between 5 and 100 characters")
    private String number;

    @NotBlank(message = "passport country is required")
    @Size(min = 2, max = 100, message = "country must be between 2 and 100 characters")
    private String country;

    @NotNull(message = "passport issuance date is required")
    private LocalDate issuanceDate;

    @NotNull(message = "passport expiration date is required")
    private LocalDate expirationDate;
}

