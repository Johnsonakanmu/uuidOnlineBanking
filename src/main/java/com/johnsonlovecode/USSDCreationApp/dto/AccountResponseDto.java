package com.johnsonlovecode.USSDCreationApp.dto;

import com.johnsonlovecode.USSDCreationApp.utils.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String accountNumber;
    private String types; //eg "SAVINGS", "CURRENT", FIXED
    private BigDecimal balance = BigDecimal.ZERO;

    // Add updated fields
    private String address;
    private String city;
    private String country;
    private Date dateOfBirth;
    private String gender;

    private Set<String> authorities;


}
