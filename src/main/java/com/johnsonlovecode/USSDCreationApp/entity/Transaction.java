package com.johnsonlovecode.USSDCreationApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private String type; // e.g. "DEPOSIT", "WITHDRAWAL"

    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}
