package org.ufcu.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ufcu.customerservice.domain.ProfileAddress;

@Repository
public interface ProfileAddressRepository extends JpaRepository<ProfileAddress, Long> {
}
