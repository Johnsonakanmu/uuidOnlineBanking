package com.johnsonlovecode.USSDCreationApp.service.impl;

import com.johnsonlovecode.USSDCreationApp.dto.*;
import com.johnsonlovecode.USSDCreationApp.entity.Account;
import com.johnsonlovecode.USSDCreationApp.entity.Transaction;
import com.johnsonlovecode.USSDCreationApp.exception.ResourceNotFoundException;
import com.johnsonlovecode.USSDCreationApp.repository.AccountRepository;
import com.johnsonlovecode.USSDCreationApp.service.AccountService;
import com.johnsonlovecode.USSDCreationApp.utils.TransactionType;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public AccountResponseDto createAccount(AccountRequestDto dto) {

        //check for duplicate
        if (accountRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("Email already exist");
        }
        if (accountRepository.existsByPhoneNumber(dto.getPhoneNumber())){
            throw new RuntimeException("Phone number already exist");
        }

        // Map DTO to Entity
        Account account = modelMapper.map(dto, Account.class);

        // Encrypt Sensitive field
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setPin(passwordEncoder.encode(dto.getPin()));

        // Generate Unique account number
        account.setAccountNumber(generateAccountNumber());

        //Default balance
        account.setBalance(BigDecimal.ZERO);

        //save account
        Account saved = accountRepository.save(account);


        // map entity to response DTO
        return modelMapper.map(saved, AccountResponseDto.class);
    }


    private String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.valueOf((long)(Math.random() * 1_000_000_0000L));
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }


    @Override
    public AccountResponseDto getAccountById(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account", "id", id)
                );

        return modelMapper.map(account, AccountResponseDto.class);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        return accounts.stream()
                .map(account -> modelMapper.map(account, AccountResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponseDto updateAccount(Long id, AccountUpdateRequestDto dto) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account", "id", id)
                );

        if (dto.getAddress() != null) {
            account.setAddress(dto.getAddress());
        }

        if (dto.getCity() != null) {
            account.setCity(dto.getCity());
        }

        if (dto.getCountry() != null) {
            account.setCountry(dto.getCountry());
        }

        if (dto.getDateOfBirth() != null) {
            account.setDateOfBirth(dto.getDateOfBirth());
        }

        if (dto.getGender() != null) {
            account.setGender(dto.getGender());
        }

        if (dto.getAccountType() != null) {
            account.setAccountType(dto.getAccountType());
        }

        Account savedAccount = accountRepository.save(account);

        return modelMapper.map(savedAccount, AccountResponseDto.class);
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

        account.getTransactions().add(transaction);


        // No need to explicitly save if @Transactional is used,
        // but keeping it is also fine
        Account saveAccount =  accountRepository.save(account);

        return modelMapper.map(saveAccount, AccountResponseDto.class);
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

        return modelMapper.map(savedAccount, AccountResponseDto.class);


    }

    @Override
    public BalanceResponseDto checkBalance(Long id) {

        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id)
        );

       BalanceResponseDto response = new BalanceResponseDto();
       response.setAccountNumber(account.getAccountNumber());
       response.setAccountName(account.getFirstName());
       response.setBalance(account.getBalance());

        return modelMapper.map(response, BalanceResponseDto.class);
    }

    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id)
        );

        accountRepository.delete(account);
    }

}
