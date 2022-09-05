package com.example.loanmanagementimalipay.exceptions;

public class NegativeLoanException extends RuntimeException {
    public NegativeLoanException(String message) {

        super(message);
    }
}
