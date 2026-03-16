package com.johnsonlovecode.USSDCreationApp.repository;

import com.johnsonlovecode.USSDCreationApp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(String accountNumber);
    boolean existsByUserIdAndTypes(Long id, String types);
    List<Account> findByUserId(Long id);

}
