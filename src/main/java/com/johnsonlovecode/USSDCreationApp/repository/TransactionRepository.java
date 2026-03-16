package com.johnsonlovecode.USSDCreationApp.repository;

import com.johnsonlovecode.USSDCreationApp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findTop10ByAccountIdOrderByTransactionDateDesc(Long accountId);

    List<Transaction> findByAccountIdAndType(Long accountId, String  type);

    List<Transaction> findByAccountIdAndTransactionDateBetween(
            Long accountId,
            LocalDateTime start,
            LocalDateTime end
    );

}
