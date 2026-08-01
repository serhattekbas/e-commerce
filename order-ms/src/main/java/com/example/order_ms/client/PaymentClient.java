package com.example.order_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-ms", url = "http://localhost:8083") // Payment Service'in adresi
public interface PaymentClient {
    @PostMapping("/payments/process")
    PaymentResponse processPayment(@RequestBody PaymentRequest request);

    @PostMapping("/payments/{paymentId}/refund")
    void refundPayment(@PathVariable Long paymentId, @RequestBody RefundRequest request);
}

// --- Ek DTO'lar (Payment Service'den gelmesi gerekenler) ---

class PaymentRequest {
    private Long orderId;
    private Long userId;
    private double amount;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
    // Getters and setters
}

class PaymentResponse {
    private Long id;
    private String status;
    private double amount;
    // Getters and setters
}

class RefundRequest {
    private Long paymentId;
    private double amount;
    private String reason;
    // Getters and setters
}
