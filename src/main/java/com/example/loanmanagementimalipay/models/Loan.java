package com.example.loanmanagementimalipay.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
//@ToString
@RequiredArgsConstructor
@Entity
@Table(name ="loans")
public class Loan {

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
    private LocalDate loanDate;
    private LocalDate dueDate;
    private BigDecimal loanAmount;
    private BigDecimal interest;
    private BigDecimal loanBalance;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private List<Payment> payment;
}
