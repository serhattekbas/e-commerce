package org.example.paymentms.service;

import lombok.RequiredArgsConstructor;
import org.example.paymentms.dto.PaymentRequest;
import org.example.paymentms.dto.PaymentResponse;
import org.example.paymentms.model.Payment;
import org.example.paymentms.repository.paymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final paymentRepository paymentRepository;

    public PaymentResponse processPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setPaymentMethod("CREDIT_CARD");
        payment.setAmount(request.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        
        // Simülasyon: Tutar > 0 ise ödeme başarılı kabul edilir
        if (request.getAmount() > 0) {
            payment.setStatus("SUCCESS");
        } else {
            payment.setStatus("FAILED");
        }

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentResponse.builder()
                .paymentId(savedPayment.getPaymentId())
                .orderId(request.getOrderId())
                .status(savedPayment.getStatus())
                .amount(savedPayment.getAmount())
                .paymentDate(savedPayment.getPaymentDate())
                .build();
    }

    public void refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Ödeme bulunamadı: " + paymentId));
        payment.setStatus("REFUND");
        paymentRepository.save(payment);
    }
}

