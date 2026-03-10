package org.ufcu.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GovernmentIdRequestDto {

    @NotBlank(message = "ID type is required")
    @Size(min = 2, max = 100, message = "ID type must be between 2 and 100 characters")
    private String idType; // Driving License, National ID, Resident Card, etc.

    @NotBlank(message = "ID value is required")
    @Size(min = 5, max = 200, message = "ID value must be between 5 and 200 characters")
    private String idValue;
}

