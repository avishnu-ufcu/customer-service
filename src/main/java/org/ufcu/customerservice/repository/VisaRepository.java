package org.ufcu.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ufcu.customerservice.domain.Visa;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Long> {
}
