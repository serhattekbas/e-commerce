package org.example.paymentms.repository;

import org.example.paymentms.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface paymentRepository extends JpaRepository<Payment, Long> {
}
