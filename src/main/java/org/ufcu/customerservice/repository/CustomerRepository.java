package org.ufcu.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ufcu.customerservice.domain.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
