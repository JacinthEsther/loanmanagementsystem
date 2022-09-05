package com.example.loanmanagementimalipay.services;

import com.example.loanmanagementimalipay.dtos.requests.AddUserRequest;
import com.example.loanmanagementimalipay.dtos.requests.LoanRequest;
import com.example.loanmanagementimalipay.dtos.responses.AddUserResponse;
import com.example.loanmanagementimalipay.dtos.responses.LoanResponse;
import com.example.loanmanagementimalipay.dtos.responses.PaymentReport;
import com.example.loanmanagementimalipay.models.Payment;
import com.example.loanmanagementimalipay.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private AddUserRequest user;
    @BeforeEach
    void setUp(){
        user = AddUserRequest.builder()
                .contactNo("0909467281").dateOfBirth("12/03/1990")
                .firstName("Esther").lastName("Jay").gender("female").email("agb@gmail.com")
                .monthlyAllowance(15000).address("H4, Lekki, Lagos")
                .build();
//        userService.delete(user);
    }

    @AfterEach
    void tearDown(){
        userService.deleteAll();
    }

    @Test
    void userCanRegisterTest() throws ParseException {


       AddUserResponse userResponse= userService.add(user);
       assertEquals(userResponse.getAge(), 32);

    }

    @Test
    void searchForUserByEmailTest() throws ParseException {
        userService.add(user);
        User foundUser = userService.searchByEmail(user.getEmail());

        assertEquals("agb@gmail.com", foundUser.getEmail());
    }

    @Test
    void searchForUserByIdTest() throws ParseException {
     AddUserResponse response =   userService.add(user);

        User foundUser = userService.searchById(response.getUserId());
        assertEquals(foundUser.getEmail(),"agb@gmail.com" );

    }

    @Test
    void userHasALoanTest() throws ParseException {
        userService.add(user);
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setEmail("agb@gmail.com");
        loanRequest.setLoanAmount(290000);
        LoanResponse response=  userService.createUserLoan(loanRequest);

      assertEquals(450.0000, response.getLoanAmount().doubleValue());
      assertEquals(459.0, response.getPayBackAmount().doubleValue());
      assertEquals("2022-10-25", response.getDueDate());

    }


    @Test
    void userCanSearchForLoanWithUserIdTest() throws ParseException {
       AddUserResponse response = userService.add(user);
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setEmail("agb@gmail.com");
        loanRequest.setLoanAmount(290000);
        userService.createUserLoan(loanRequest);

        // user.getEmail();
        // loan.getId();
        // user.getId();
       BigDecimal loanAmount = userService.searchForLoan(response.getUserId());

        assertEquals(459.0, loanAmount.doubleValue());


    }

    @Test
    void userCanMakePaymentToLoan() throws ParseException {
         userService.add(user);
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setEmail("agb@gmail.com");
        loanRequest.setLoanAmount(290000);
        userService.createUserLoan(loanRequest);

      PaymentReport loanBalance =  userService.makePayment(452.0,user.getEmail());

      assertEquals(7.00, loanBalance.getLoanBalance().doubleValue());
    }


    @Test
    void searchForPaymentTest() throws ParseException {
        userService.add(user);
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setEmail("agb@gmail.com");
        loanRequest.setLoanAmount(290000);
        userService.createUserLoan(loanRequest);

        userService.makePayment(300.0,user.getEmail());
        userService.makePayment(100.0,user.getEmail());

        List<Payment> payments =userService.searchForPayment(user.getEmail());

        assertEquals(2, payments.size());
    }

}