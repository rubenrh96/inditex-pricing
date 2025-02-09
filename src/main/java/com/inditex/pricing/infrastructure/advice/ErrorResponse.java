package com.inditex.pricing.infrastructure.advice;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}
