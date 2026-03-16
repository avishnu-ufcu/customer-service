package org.ufcu.customerservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufcu.customerservice.repository.CustomerRepository;
import java.time.Year;

@Service
@Slf4j
public class UidGeneratorService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Generate UID in format: UID-YYYY-1XXXXXXX
     * Example: UID-2026-10000001
     */
    public String generateUID() {
        int currentYear = Year.now().getValue();

        Long sequence = customerRepository.getNextUidSequence();

        // Format: UID-2026-1XXXXXXX (1 prefix + 7 digits)
        String uid = String.format("UID-%d-%d%07d", currentYear, 1, sequence);

        log.info("Generated UID: {}", uid);
        return uid;
    }
}
