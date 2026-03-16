package com.johnsonlovecode.USSDCreationApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {

    private Long transactionId;
    private BigDecimal amount;
    private String type;
    private LocalDateTime transactionDate;
    private String accountNumber;

}
