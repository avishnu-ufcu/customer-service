package org.ufcu.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ufcu.customerservice.domain.GovernmentId;

@Repository
public interface GovernmentIdRepository extends JpaRepository<GovernmentId, Long> {
}
