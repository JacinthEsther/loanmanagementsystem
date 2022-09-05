package com.example.loanmanagementimalipay.exceptions;

public class OverPaymentException extends RuntimeException {
    public OverPaymentException(String message) {
        super(message);
    }
}
