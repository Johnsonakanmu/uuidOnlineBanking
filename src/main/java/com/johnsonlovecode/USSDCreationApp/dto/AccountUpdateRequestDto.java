package com.johnsonlovecode.USSDCreationApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateRequestDto {

    private String address;
    private String city;
    private String country;
    private Date dateOfBirth;
    private String gender;
    private String accountType;
}
