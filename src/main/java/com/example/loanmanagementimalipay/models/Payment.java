package com.example.loanmanagementimalipay.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
//@ToString
//@JsonDeserialize

@RequiredArgsConstructor
@Entity
@Table(name ="payments")
public class Payment {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName= "user_sequence"

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;



    private LocalDate paymentDate;
    private BigDecimal paymentAmount;
}
