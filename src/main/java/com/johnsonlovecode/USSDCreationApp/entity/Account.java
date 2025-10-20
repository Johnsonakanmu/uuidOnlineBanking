package com.johnsonlovecode.USSDCreationApp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = true)
    private String password;
    @Column(nullable = false, unique = true, length = 10)
    private String accountNumber;
    @Column(nullable = false)
    private String pin;
    @Column(nullable = true)
    private String address;
    @Column(nullable = true)
    private String city;
    @Column(nullable = true)
    private String  country;
    @Column(nullable = true)
    private Date dateOfBirth;
    @Column(nullable = true)
    private String  gender;
    @Column(nullable = true)
    private String  accountType;
    @Column(nullable = true)
    private Double balance =0.0;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

}
