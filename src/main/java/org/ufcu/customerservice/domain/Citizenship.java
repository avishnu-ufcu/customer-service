package org.ufcu.customerservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Citizenship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String ssnOrItin;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "citizenship", cascade = CascadeType.ALL, orphanRemoval = true)
    private Passport passports;

    @OneToOne(mappedBy = "citizenship", cascade = CascadeType.ALL, orphanRemoval = true)
    private Visa visas;

    @OneToMany(mappedBy = "citizenship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GovernmentId> governmentIds;
}
