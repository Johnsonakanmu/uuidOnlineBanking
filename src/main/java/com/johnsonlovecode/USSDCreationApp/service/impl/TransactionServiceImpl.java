package com.johnsonlovecode.USSDCreationApp.service.impl;

import com.johnsonlovecode.USSDCreationApp.dto.TransactionResponseDto;
import com.johnsonlovecode.USSDCreationApp.entity.Transaction;
import com.johnsonlovecode.USSDCreationApp.repository.TransactionRepository;
import com.johnsonlovecode.USSDCreationApp.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl  implements TransactionService {

    private TransactionRepository transactionRepository;

    // Transaction History
    @Override
    public List<TransactionResponseDto> getTransactionHistory(Long transactionId) {

        List<Transaction> transactions = transactionRepository.findByAccountId(transactionId);

        return transactions.stream()
                .map(transaction -> new TransactionResponseDto(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getTransactionDate(),
                        transaction.getAccount().getAccountNumber()
                ))
                .toList();

    }

    // Last 10 Transactions
    @Override
    public List<TransactionResponseDto> getLast10Transactions(Long accountId) {
        List<Transaction> transactions =
                transactionRepository.findTop10ByAccountIdOrderByTransactionDateDesc(accountId);

        return transactions.stream()
                .map(transaction -> new TransactionResponseDto(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getTransactionDate(),
                        transaction.getAccount().getAccountNumber()
                ))
                .toList();
    }

    // Monthly Statement
    @Override
    public List<TransactionResponseDto> getTransactionsByDateRange(Long accountId, LocalDateTime start, LocalDateTime end) {

        List<Transaction> transactions =
                transactionRepository.findByAccountIdAndTransactionDateBetween(accountId, start, end);

        return transactions.stream()
                .map(t -> new TransactionResponseDto(
                        t.getId(),
                        t.getAmount(),
                        t.getType(),
                        t.getTransactionDate(),
                        t.getAccount().getAccountNumber()
                ))
                .toList();
    }

    @Override
    public List<TransactionResponseDto> filterTransactionsByType(Long accountId, String type) {

        List<Transaction> transactions =
                transactionRepository.findByAccountIdAndType(accountId, type);

        return transactions.stream()
                .map(transaction -> new TransactionResponseDto(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getTransactionDate(),
                        transaction.getAccount().getAccountNumber()
                ))
                .toList();


    }

}
