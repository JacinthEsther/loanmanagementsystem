package com.example.loanmanagementimalipay.dtos.requests;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequest {
    private String email;
    private double loanAmount;
}
