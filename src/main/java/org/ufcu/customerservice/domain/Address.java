package org.ufcu.customerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}

