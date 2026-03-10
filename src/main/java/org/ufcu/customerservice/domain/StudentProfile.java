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
public class StudentProfile extends Profile {

    private String universityName;
    private String studentId;
    private String i20Number;
    private LocalDate expectedGraduation;
}