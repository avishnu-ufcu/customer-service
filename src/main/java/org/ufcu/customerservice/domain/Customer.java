package org.ufcu.customerservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ufcu.customerservice.util.HumanReadableIDGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", unique = true, nullable = false, length = 36)
    private String customerId;

    @Column(name = "human_readable_customer_id")
    private String humanReadCustomerId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String country;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerAddress> addresses;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Citizenship citizenship;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profile> profiles;

    @PrePersist
    public void generateCustomerId() {
        if (this.customerId == null) {
            UUID uuid = UUID.randomUUID();
            this.customerId = uuid.toString();
            // Human readable ID generate
            this.humanReadCustomerId =
                    HumanReadableIDGenerator.generateHumanReadableID("OB", uuid);
        }
    }
}
