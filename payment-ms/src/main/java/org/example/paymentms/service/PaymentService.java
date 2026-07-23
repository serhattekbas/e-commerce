package org.example.paymentms.service;

import lombok.AllArgsConstructor;
import org.example.paymentms.repository.paymentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class PaymentService {

    private final paymentRepository paymentRepository;


}
