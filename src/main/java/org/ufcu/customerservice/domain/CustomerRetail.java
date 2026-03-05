package org.ufcu.customerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerRetail extends Party {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private PartyIdentification partyIdentification;
    private String email;
    private String phone;
    private Address address;
    private String status;
}

