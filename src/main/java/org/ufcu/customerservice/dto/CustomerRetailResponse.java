package org.ufcu.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerRetailResponse {
    private String caseId;
    private String customerId;
    private String status;
    private String action;
    private ZonedDateTime createdAt;
}

