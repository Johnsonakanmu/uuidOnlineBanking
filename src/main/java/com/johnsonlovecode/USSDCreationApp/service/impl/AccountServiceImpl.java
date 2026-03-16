package com.johnsonlovecode.USSDCreationApp.service.impl;

import com.johnsonlovecode.USSDCreationApp.dto.*;
import com.johnsonlovecode.USSDCreationApp.entity.Account;
import com.johnsonlovecode.USSDCreationApp.entity.Transaction;
import com.johnsonlovecode.USSDCreationApp.entity.User;
import com.johnsonlovecode.USSDCreationApp.exception.ResourceNotFoundException;
import com.johnsonlovecode.USSDCreationApp.repository.AccountRepository;
import com.johnsonlovecode.USSDCreationApp.repository.UserRepository;
import com.johnsonlovecode.USSDCreationApp.service.AccountService;
import com.johnsonlovecode.USSDCreationApp.utils.AccountType;
import com.johnsonlovecode.USSDCreationApp.utils.TransactionType;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Override
    public AccountResponseDto getAccountById(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account", "id", id)
                );

        User user = account.getUser();

        AccountResponseDto response = new AccountResponseDto();

        response.setId(account.getId());
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAccountNumber(account.getAccountNumber());
        if (account.getTypes() != null) {
            try {
                AccountType type = AccountType.valueOf(account.getTypes().toUpperCase());
                account.setTypes(type.name());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid account type. Must be SAVINGS, CURRENT, or FIXED.");
            }
        }        response.setBalance(account.getBalance());

        return response;
    }

    @Override
    public UserAccountResponseDto getUserWithMultipleAccountTypes(Long id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        List<AccountDto> accountList = user.getAccounts()
                .stream()
                .map(account -> {
                    AccountDto dto = new AccountDto();
                    dto.setId(account.getId());
                    dto.setAccountNumber(account.getAccountNumber());
                    dto.setTypes(account.getTypes());
                    dto.setBalance(account.getBalance());
                    return dto;
                        })
                .toList();

        UserAccountResponseDto response = new UserAccountResponseDto();
        response.setUserId(user.getId());
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAccounts(accountList);

        return response;
    }


    @Override
    public List<AccountResponseDto> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        List<AccountResponseDto> responses = new ArrayList<>();

        for (Account account : accounts){
            User user = account.getUser();

            AccountResponseDto response = new AccountResponseDto();
            response.setId(account.getId());
            response.setFullName(user.getFirstName() + " " + user.getLastName());
            response.setEmail(user.getEmail());
            response.setPhoneNumber(user.getPhoneNumber());
            response.setAccountNumber(account.getAccountNumber());
            response.setTypes(account.getTypes());
            response.setBalance(account.getBalance());

            responses.add(response);
        }
        return responses;

    }


    @Override
    public AccountResponseDto updateAccount(Long id, AccountUpdateRequestDto dto) {

        // Fetch the account
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));

        // Update only fields provided in the DTO (keep existing values if null)
        account.setAddress(dto.getAddress() != null ? dto.getAddress() : account.getAddress());
        account.setCity(dto.getCity() != null ? dto.getCity() : account.getCity());
        account.setCountry(dto.getCountry() != null ? dto.getCountry() : account.getCountry());
        account.setDateOfBirth(dto.getDateOfBirth() != null ? dto.getDateOfBirth() : account.getDateOfBirth());
        account.setGender(dto.getGender() != null ? dto.getGender() : account.getGender());

        // Update account type if provided and valid
        if (dto.getAccountType() != null) {
            try {
                AccountType type = AccountType.valueOf(dto.getAccountType().toUpperCase());
                account.setTypes(type.name());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid account type. Must be SAVINGS, CURRENT, or FIXED.");
            }
        }

        // Save updated account
        Account savedAccount = accountRepository.save(account);

        // Map to response DTO
        User user = savedAccount.getUser();
        AccountResponseDto response = new AccountResponseDto();
        response.setId(savedAccount.getId());
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAccountNumber(savedAccount.getAccountNumber());
        response.setBalance(savedAccount.getBalance());
        response.setAddress(savedAccount.getAddress());
        response.setCity(savedAccount.getCity());
        response.setCountry(savedAccount.getCountry());
        response.setDateOfBirth(savedAccount.getDateOfBirth());
        response.setGender(savedAccount.getGender());
        response.setTypes(savedAccount.getTypes()); // ✅ correct account type in response
        response.setAuthorities(savedAccount.getUser().getRoles());
        return response;
    }


    @Transactional
    @Override
    public AccountResponseDto depositInToAccount(Long id, BigDecimal amount) {

        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id)
        );

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new   IllegalArgumentException("Deposit amount must be greater than 0");
        }

        // update balance safety using BigDecimal
      account.setBalance(account.getBalance().add(amount));

        // save transaction record
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT.name());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAccount(account);

        // Add transaction to account
        account.getTransactions().add(transaction);
        // Save account
        Account savedAccount =  accountRepository.save(account);
        // Map Manually to response DTo
        User user = savedAccount.getUser();

        AccountResponseDto response = new AccountResponseDto();
        response.setId(savedAccount.getId());
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAccountNumber(savedAccount.getAccountNumber());
        response.setTypes(savedAccount.getTypes());
        response.setBalance(savedAccount.getBalance());


        // Include optional fields if your response DTO has them
        response.setAddress(savedAccount.getAddress());
        response.setCity(savedAccount.getCity());
        response.setCountry(savedAccount.getCountry());
        response.setDateOfBirth(savedAccount.getDateOfBirth());
        response.setGender(savedAccount.getGender());

        return response;
    }

    @Transactional
    @Override
    public AccountResponseDto withdrawFromAccount(Long id, BigDecimal amount) {

        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id)
        );

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0");
        }

        if (account.getBalance().compareTo(amount) < 0){
            throw new IllegalArgumentException("Insufficient balance");

        }

        // Deduct Balance
        account.setBalance(account.getBalance().subtract(amount));

        //Create Transaction record
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAWAL.name());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAccount(account);

        account.getTransactions().add(transaction);

        Account savedAccount = accountRepository.save(account);

        // 7️⃣ Map manually to response DTO
        User user = savedAccount.getUser();

        AccountResponseDto response = new AccountResponseDto();
        response.setId(savedAccount.getId());
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAccountNumber(savedAccount.getAccountNumber());
        response.setTypes(savedAccount.getTypes());
        response.setBalance(savedAccount.getBalance());

        // Include optional fields if DTO has them
        response.setAddress(savedAccount.getAddress());
        response.setCity(savedAccount.getCity());
        response.setCountry(savedAccount.getCountry());
        response.setDateOfBirth(savedAccount.getDateOfBirth());
        response.setGender(savedAccount.getGender());

        return response;

    }

    @Override
    public BalanceResponseDto checkBalance(Long id) {

        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id)
        );

        // Map manually to BalanceResponseDto
        User user = account.getUser();

       BalanceResponseDto response = new BalanceResponseDto();
       response.setAccountNumber(account.getAccountNumber());
       response.setBalance(account.getBalance());


        // Optional: include full name
        response.setAccountName(user.getFirstName() + " " + user.getLastName());


        return response;
    }

    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id)
        );

        accountRepository.delete(account);
    }

}
