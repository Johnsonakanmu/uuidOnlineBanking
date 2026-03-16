package com.johnsonlovecode.USSDCreationApp.dto;


import com.johnsonlovecode.USSDCreationApp.utils.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAccountRequestDto {

    private AccountType type;
    private String pin;
}
