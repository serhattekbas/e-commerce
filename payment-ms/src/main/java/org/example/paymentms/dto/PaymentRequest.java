package org.example.paymentms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private Long orderId;
    private Long userId;
    private double amount;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
}
