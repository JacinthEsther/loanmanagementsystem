package com.example.loanmanagementimalipay.services;


import com.example.loanmanagementimalipay.dtos.requests.AddUserRequest;
import com.example.loanmanagementimalipay.dtos.requests.LoanRequest;
import com.example.loanmanagementimalipay.dtos.responses.AddUserResponse;
import com.example.loanmanagementimalipay.dtos.responses.LoanResponse;
import com.example.loanmanagementimalipay.dtos.responses.PaymentReport;
import com.example.loanmanagementimalipay.exceptions.NegativeLoanException;
import com.example.loanmanagementimalipay.exceptions.NegativePaymentException;
import com.example.loanmanagementimalipay.exceptions.NotEligibleException;
import com.example.loanmanagementimalipay.exceptions.UserNotFoundException;
import com.example.loanmanagementimalipay.models.Gender;
import com.example.loanmanagementimalipay.models.Loan;
import com.example.loanmanagementimalipay.models.Payment;
import com.example.loanmanagementimalipay.models.User;
import com.example.loanmanagementimalipay.repositories.LoanRepository;
import com.example.loanmanagementimalipay.repositories.PaymentRepository;
import com.example.loanmanagementimalipay.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final PaymentRepository paymentRepository;

    
    @Override
    public AddUserResponse add(AddUserRequest user) throws ParseException {

        User addUser = new User();
        addUser.setFullName(user.getFirstName().toUpperCase() + " " + user.getLastName().toUpperCase());
        addUser.setContactNo(user.getContactNo());
        addUser.setEmail(user.getEmail().toLowerCase());
        addUser.setGender(Gender.valueOf(user.getGender().toUpperCase()));
        addUser.setIncome(BigDecimal.valueOf(user.getMonthlyAllowance()));
        addUser.setAddress(user.getAddress());

        LocalDate givenDate = getLocalDate(user);
        Period period = Period.between(givenDate, LocalDate.now());

        addUser.setDateOfBirth(givenDate);
        addUser.setAge(period.getYears());

        User savedUser=userRepository.save(addUser);
        AddUserResponse response = new AddUserResponse();
        response.setAge(savedUser.getAge());
        response.setMessage("created successfully");
        response.setUserId(savedUser.getId());
        return response;
    }

    private LocalDate getLocalDate(AddUserRequest user) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(user.getDateOfBirth());
        Instant instant = date.toInstant();
        ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
        return zone.toLocalDate();
    }

    @Override
    public User searchByEmail(String email) {

        return userRepository.findByEmail(email.toLowerCase()).orElseThrow(()->new UserNotFoundException("user does not exist"));
    }

    @Override
    public void deleteAll() {

      userRepository.deleteAll();
    }

    @Override
    public User searchById(long id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException("user does not exist"));
    }

    @Override
    public LoanResponse createUserLoan(LoanRequest loanRequest) {
      User user =  userRepository.findByEmail(loanRequest.getEmail().toLowerCase())
              .orElseThrow(()->new UserNotFoundException("user does not exist"));
       LoanResponse response = new LoanResponse();
       if(user.getLoan()== null) {

           Loan loan = new Loan();
           if (loanRequest.getLoanAmount() < 0.1) {
               throw new NegativeLoanException("Loan cannot be negative or 0.0");
           }
           if (user.getAge() >= 18 && loanRequest.getLoanAmount() > 0.0) {

               loan.setLoanAmount(user.getIncome().multiply(BigDecimal.valueOf(0.03)));
               loan.setInterest(BigDecimal.valueOf((0.02)).multiply(loan.getLoanAmount()));
               loan.setLoanDate(LocalDate.now());
               loan.setLoanBalance(loan.getLoanAmount().add(loan.getInterest()));

               LocalDate dueDate = loan.getLoanDate().plusDays(50);

               loan.setDueDate(dueDate);
               loan.setUser(user);
               Loan savedLoan = loanRepository.save(loan);
               user.setLoan(savedLoan);

               User savedUser = userRepository.save(user);

               response.setLoanAmount(savedUser.getLoan().getLoanAmount());
               response.setPayBackAmount(savedUser.getLoan().getLoanBalance());
               response.setDueDate(savedUser.getLoan().getDueDate().toString());


           }
           return response;
       }
       else throw new NotEligibleException("Please Repay #"+ user.getLoan().getLoanAmount() + " to get more Loan");
    }

    @Override
    public BigDecimal searchForLoan(Long userId) {
        User user =userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("user does not exist"));

        return user.getLoan().getLoanBalance();
    }

    @Override
    public BigDecimal searchForLoan(String userEmail) {
        User user =  userRepository.findByEmail(userEmail.toLowerCase())
                .orElseThrow(()->new UserNotFoundException("user does not exist"));

        return user.getLoan().getLoanBalance();
    }

    @Override
    public PaymentReport makePayment(double amount, String email) {
        User user =  userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(()->new UserNotFoundException("user does not exist"));


        Loan loan= loanRepository.findById(user.getLoan().getId()).orElseThrow();
        Payment payment = new Payment();
//        log.info("i got here........... "+amount);

        if(amount <= 0.0) {
            throw new NegativePaymentException("You have not paid any amount");
        }

                payment.setPaymentAmount(BigDecimal.valueOf(amount));
//            log.info("i got here........... " + payment.getPaymentAmount());
                payment.setPaymentDate(LocalDate.now());

                loan.setLoanBalance(loan.getLoanBalance().subtract(payment.getPaymentAmount()));
                paymentRepository.save(payment);
                loan.getPayment().add(payment);
                Loan savedLoan = loanRepository.save(loan);

                user.setLoan(savedLoan);
                User savedUser = userRepository.save(user);
                if (savedUser.getLoan().getLoanAmount().doubleValue() == 0.0) {
                    savedUser.setLoan(null);
                    userRepository.save(savedUser);
                }

                PaymentReport paymentReport = new PaymentReport();

                paymentReport.setPaymentDate(LocalDate.now().toString());
                paymentReport.setPaymentTime(LocalDateTime.now());
                paymentReport.setLoanBalance(savedLoan.getLoanBalance());

                return paymentReport;

    }

    @Override
    public List<Payment> searchForPayment(String email) {
       User user= userRepository.findByEmail(email).orElseThrow();
      Loan loan =  loanRepository.findById(user.getLoan().getId()).orElseThrow();
        return loan.getPayment();
    }


}
