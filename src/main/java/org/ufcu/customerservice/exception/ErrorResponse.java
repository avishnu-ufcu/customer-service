package org.ufcu.customerservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
//    private String details;
//    private ZonedDateTime timestamp;
}

