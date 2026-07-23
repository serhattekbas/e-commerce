package org.example.paymentms.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymentms.repository.paymentRepository;
import org.example.paymentms.service.PaymentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor

public class PaymentController {

    private final PaymentService paymentService;

}
