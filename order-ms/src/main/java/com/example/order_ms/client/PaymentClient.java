package com.example.order_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-ms", url = "http://localhost:8083")
public interface PaymentClient {
    @PostMapping("/api/payment/process")
    PaymentResponse processPayment(@RequestBody PaymentRequest request);

    @PostMapping("/api/payment/{paymentId}/refund")
    void refundPayment(@PathVariable("paymentId") Long paymentId, @RequestBody RefundRequest request);

    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class PaymentRequest {
        private Long orderId;
        private Long userId;
        private double amount;
        private String cardNumber;
        private String cardHolderName;
        private String expirationDate;
        private String cvv;
    }

    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class PaymentResponse {
        private Long id;
        private String status;
        private double amount;
    }

    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class RefundRequest {
        private Long paymentId;
        private double amount;
        private String reason;
    }
}
