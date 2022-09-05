package com.example.loanmanagementimalipay.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {
    private BigDecimal loanAmount;
    private BigDecimal payBackAmount;
    private String dueDate;
}
