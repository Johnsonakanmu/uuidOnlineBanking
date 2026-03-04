package com.johnsonlovecode.USSDCreationApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String accountNumber;
    private String address;
    private String city;
    private String accountType;
    private String country;
    private Date dateOfBirth;
    private String gender;
    private BigDecimal balance = BigDecimal.ZERO;
}
