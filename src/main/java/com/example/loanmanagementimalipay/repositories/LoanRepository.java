package com.example.loanmanagementimalipay.repositories;

import com.example.loanmanagementimalipay.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
