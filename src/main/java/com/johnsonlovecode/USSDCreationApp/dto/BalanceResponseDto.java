package com.johnsonlovecode.USSDCreationApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceResponseDto {

    private String accountNumber;
    private String accountName;
    private BigDecimal balance;
}
