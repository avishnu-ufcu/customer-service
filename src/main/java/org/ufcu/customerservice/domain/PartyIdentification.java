package org.ufcu.customerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PartyIdentification {
    private String partyIdentificationType;
    private String partyIdentificationValue;
}

