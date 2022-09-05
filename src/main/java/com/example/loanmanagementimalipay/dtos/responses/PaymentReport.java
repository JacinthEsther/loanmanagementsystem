package com.example.loanmanagementimalipay.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReport {

    private BigDecimal loanBalance;
    private String paymentDate;
    private LocalDateTime paymentTime;

}
