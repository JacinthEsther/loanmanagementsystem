package com.example.loanmanagementimalipay.exceptions;

public class NegativePaymentException extends RuntimeException{

    public NegativePaymentException(String message) {

        super(message);
    }
}
