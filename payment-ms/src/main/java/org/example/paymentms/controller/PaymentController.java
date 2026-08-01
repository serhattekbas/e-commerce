package org.example.paymentms.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymentms.dto.PaymentRequest;
import org.example.paymentms.dto.PaymentResponse;
import org.example.paymentms.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<Void> refundPayment(@PathVariable Long paymentId) {
        paymentService.refundPayment(paymentId);
        return ResponseEntity.ok().build();
    }
}

