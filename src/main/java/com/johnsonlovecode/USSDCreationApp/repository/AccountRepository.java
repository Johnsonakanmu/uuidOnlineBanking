package com.johnsonlovecode.USSDCreationApp.repository;

import com.johnsonlovecode.USSDCreationApp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByAccountNumber(String accountNumber);
}
