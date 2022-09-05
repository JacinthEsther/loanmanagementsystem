package com.example.loanmanagementimalipay.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String contactNo;
    private String dateOfBirth;
    private int age;
    private String address;
    private double monthlyAllowance;
}
