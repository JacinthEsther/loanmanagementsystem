package com.example.loanmanagementimalipay.controllers;

import com.example.loanmanagementimalipay.dtos.requests.AddUserRequest;
import com.example.loanmanagementimalipay.dtos.requests.LoanRequest;
import com.example.loanmanagementimalipay.exceptions.UserNotFoundException;
import com.example.loanmanagementimalipay.services.UserService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("api/v1/loanManagementSystem")
@Slf4j
public class LoanManagementController {

    @Autowired
    private UserService service;

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody AddUserRequest request) {

        try {
            return new ResponseEntity<>(service.add(request), HttpStatus.CREATED);
        } catch (UserNotFoundException | ParseException ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

//      "age": 22,
//  "message": "created successfully",
//  "userId": 752

    @GetMapping("/loanSystem/email/{email}/get")
    public ResponseEntity<?> findUserByEmail(@PathVariable String email) {
        try {
            return new ResponseEntity<>(service.searchByEmail(email), HttpStatus.FOUND);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{Id}")
    public ResponseEntity<?> searchForUserById(@PathVariable Long Id) {
        try {
            return new ResponseEntity<>(service.searchById(Id), HttpStatus.FOUND);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/loan/createLoan")
    public ResponseEntity<?> createLoan(@RequestBody LoanRequest request) {

        try {
            return new ResponseEntity<>(service.createUserLoan(request), HttpStatus.CREATED);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @GetMapping("/search/loan/{email}")
    public ResponseEntity<?> searchForLoan(@PathVariable String email) {
        try {
            return new ResponseEntity<>(service.searchForLoan(email), HttpStatus.FOUND);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{email}")
    public ResponseEntity<?> makePayment(@PathVariable String email, @RequestParam  double amount) {

        try {
            log.info(String.valueOf(amount));
            return new ResponseEntity<>(service.makePayment(amount,email), HttpStatus.CREATED);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/payments/{email}")
    public ResponseEntity<?> searchForPayment(@PathVariable String email) {
        try {
            return new ResponseEntity<>(service.searchForPayment(email), HttpStatus.FOUND);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
