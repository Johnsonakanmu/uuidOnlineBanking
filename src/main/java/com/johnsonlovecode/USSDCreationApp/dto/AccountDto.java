package com.johnsonlovecode.USSDCreationApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;
    @NotBlank(message = "User first name should not be null or empty")
    private String firstName;
    @NotBlank(message = "User last name should not be null or empty")
    private String lastName;
    @NotBlank(message = "User Email should not be null or empty")
    @Email(message = "Email address should be valid")
    private String email;
    @NotBlank(message = "User phone number should not be null or empty")
    private String phoneNumber;
    private String password;
    private String accountNumber;
    private String pin;
    private String address;
    private String city;
    private String  accountType;
    private String  country;
    private Date dateOfBirth;
    private String  gender;
    private Double balance;

    private List<TransactionDto> transactions;
}
