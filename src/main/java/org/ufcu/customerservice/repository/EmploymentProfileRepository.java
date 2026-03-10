package org.ufcu.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ufcu.customerservice.domain.EmploymentProfile;

@Repository
public interface EmploymentProfileRepository extends JpaRepository<EmploymentProfile, Long> {
}
