package org.ufcu.customerservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passportNumber;
    private String country;
    private LocalDate issuanceDate;
    private LocalDate expirationDate;

    @OneToOne
    @JoinColumn(name = "citizenship_id",unique = true)
    private Citizenship citizenship;
}
