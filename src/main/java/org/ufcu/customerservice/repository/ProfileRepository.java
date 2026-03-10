package org.ufcu.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ufcu.customerservice.domain.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
