package com.example.loanmanagementimalipay.services;

import com.example.loanmanagementimalipay.dtos.requests.AddUserRequest;
import com.example.loanmanagementimalipay.dtos.requests.LoanRequest;
import com.example.loanmanagementimalipay.dtos.responses.AddUserResponse;
import com.example.loanmanagementimalipay.dtos.responses.LoanResponse;
import com.example.loanmanagementimalipay.dtos.responses.PaymentReport;
import com.example.loanmanagementimalipay.models.Payment;
import com.example.loanmanagementimalipay.models.User;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface UserService {
    AddUserResponse add(AddUserRequest user) throws ParseException;

    User searchByEmail(String email);

    void deleteAll();

    User searchById(long id);

    LoanResponse createUserLoan(LoanRequest loanRequest);

    BigDecimal searchForLoan(Long userId);

    BigDecimal searchForLoan(String userEmail);

     PaymentReport makePayment(double amount, String email);

    List<Payment> searchForPayment(String email);
}
