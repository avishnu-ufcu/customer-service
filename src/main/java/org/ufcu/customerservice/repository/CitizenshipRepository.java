package org.ufcu.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ufcu.customerservice.domain.Citizenship;

@Repository
public interface CitizenshipRepository extends JpaRepository<Citizenship, Long> {
}
