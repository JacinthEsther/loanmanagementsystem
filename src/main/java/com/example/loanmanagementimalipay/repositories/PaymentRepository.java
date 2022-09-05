package com.example.loanmanagementimalipay.repositories;

import com.example.loanmanagementimalipay.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
