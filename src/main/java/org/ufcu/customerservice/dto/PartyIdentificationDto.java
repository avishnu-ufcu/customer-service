package org.ufcu.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PartyIdentificationDto {
    @NotBlank(message = "partyIdentificationType is required and cannot be null or empty")
    private String partyIdentificationType;

    @NotBlank(message = "partyIdentificationValue is required and cannot be null or empty")
    private String partyIdentificationValue;
}


