package org.ufcu.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ufcu.customerservice.domain.Customer;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "SELECT nextval('ufcu_db.uid_sequence')", nativeQuery = true)
    Long getNextUidSequence();

    Optional<Customer> findByCustomerId(String customerId); // or findByUid()
}
