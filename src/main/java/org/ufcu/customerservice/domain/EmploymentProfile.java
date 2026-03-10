package org.ufcu.customerservice.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmploymentProfile extends Profile {

    private String organizationName;
    private String h1bOrEadNumber;
    private LocalDate employmentStart;
}
