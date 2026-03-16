package com.johnsonlovecode.USSDCreationApp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.johnsonlovecode.USSDCreationApp.utils.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String pin; // encrypted

    @Column(nullable = false, unique = true, length = 10)
    private String accountNumber;

    private String address;
    private String city;

    private String types; //eg "SAVINGS", "CURRENT", FIXED

    private String country;
    private Date dateOfBirth;
    private String gender;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    // Relationship
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // If you want transactions
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

}
