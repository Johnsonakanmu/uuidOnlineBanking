package com.johnsonlovecode.USSDCreationApp.service;

import com.johnsonlovecode.USSDCreationApp.dto.TransactionResponseDto;
import com.johnsonlovecode.USSDCreationApp.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    List<TransactionResponseDto> getTransactionHistory(Long transactionId);

    List<TransactionResponseDto> getLast10Transactions(Long transactionId);

    List<TransactionResponseDto> getTransactionsByDateRange(Long accountId, LocalDateTime start, LocalDateTime end);

    // Monthly Statement

    List<TransactionResponseDto> filterTransactionsByType(Long accountId, String type);


}
