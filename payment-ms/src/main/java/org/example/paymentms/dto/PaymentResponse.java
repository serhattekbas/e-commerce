package org.example.paymentms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long orderId;
    private String status;
    private double amount;
    private LocalDateTime paymentDate;
}
