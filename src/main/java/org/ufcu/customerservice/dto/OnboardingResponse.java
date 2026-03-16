package org.ufcu.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OnboardingResponse {

    private String caseId;
    private String customerId;
    private String uid;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String status;
    private String message;
    private ZonedDateTime createdAt;
}
