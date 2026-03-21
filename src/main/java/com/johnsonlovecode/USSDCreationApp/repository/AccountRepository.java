package com.johnsonlovecode.USSDCreationApp.repository;

import com.johnsonlovecode.USSDCreationApp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(String accountNumber);
    boolean existsByUserIdAndTypes(Long id, String types);
    List<Account> findByUserId(Long id);

    @Query("SELECT a FROM Account a WHERE LOWER(a.user.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(a.user.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Account> findAccountsByUserName(@Param("name") String name);

}
