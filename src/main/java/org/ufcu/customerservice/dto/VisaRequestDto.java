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
public class VisaRequestDto {

    @NotBlank(message = "visa type is required")
    @Size(min = 1, max = 50, message = "visa type must be between 1 and 50 characters")
    private String type; // B-1, F-1, H1B, L1, etc.

    @NotBlank(message = "visa number is required")
    @Size(min = 5, max = 100, message = "visa number must be between 5 and 100 characters")
    private String number;

    @NotNull(message = "visa issue date is required")
    private LocalDate issueDate;

    @NotNull(message = "visa expiry date is required")
    private LocalDate expiryDate;
}

