package org.ufcu.customerservice.repository;

import org.ufcu.customerservice.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface CustomerSearchRepository extends CrudRepository<Customer, Long> {
    // US Citizen: Find by SSN/ITIN using primary/foreign key relationship
    @Query("SELECT c FROM Customer c JOIN Citizenship ci ON ci.customer.id = c.id WHERE ci.ssnOrItin = :ssnOrItin")
    Optional<Customer> findBySsnOrItin(@Param("ssnOrItin") String ssnOrItin);

    // Non-US Citizen: Find by passport number and country using primary/foreign key relationship
    @Query("SELECT c FROM Customer c JOIN Citizenship ci ON ci.customer.id = c.id JOIN Passport p ON p.citizenship.id = ci.id WHERE p.passportNumber = :passportNumber AND p.country = :passportCountry")
    Optional<Customer> findByPassportNumberAndCountry(@Param("passportNumber") String passportNumber, @Param("passportCountry") String passportCountry);

    // Fallback: Find by full name, dob, and passport number using primary/foreign key relationship
    @Query("SELECT c FROM Customer c JOIN Citizenship ci ON ci.customer.id = c.id JOIN Passport p ON p.citizenship.id = ci.id WHERE c.firstName = :firstName AND c.lastName = :lastName AND c.dateOfBirth = :dateOfBirth AND p.passportNumber = :passportNumber")
    Optional<Customer> findByFullNameDobAndPassport(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("dateOfBirth") java.time.LocalDate dateOfBirth, @Param("passportNumber") String passportNumber);


}
