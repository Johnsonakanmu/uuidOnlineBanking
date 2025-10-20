package com.johnsonlovecode.USSDCreationApp.service.impl;

import com.johnsonlovecode.USSDCreationApp.dto.AccountDto;
import com.johnsonlovecode.USSDCreationApp.entity.Account;
import com.johnsonlovecode.USSDCreationApp.entity.Transaction;
import com.johnsonlovecode.USSDCreationApp.exception.ResourceNotFoundException;
import com.johnsonlovecode.USSDCreationApp.repository.AccountRepository;
import com.johnsonlovecode.USSDCreationApp.service.AccountService;
import com.johnsonlovecode.USSDCreationApp.utils.AccountNumberGenerator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private ModelMapper modelMapper;

    private AccountNumberGenerator accountNumberGenerator;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {

        Account account = modelMapper.map(accountDto, Account.class);

        account.setAccountNumber(accountNumberGenerator.generateAccountNumber());

        // General and set account Number
        Account savedAccount = accountRepository.save(account);

        AccountDto saveAccountDto = modelMapper.map(savedAccount, AccountDto.class);

        return saveAccountDto;

    }

    @Override
    public AccountDto getAccountById(Long id) {

        Account accountDto = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id",  id)
        );
        return modelMapper.map(accountDto, AccountDto.class);
    }

    @Override
    public List<AccountDto> getAllAccounts() {

        List<Account> account = accountRepository.findAll();

        return account.stream().map(
                (accounts) -> modelMapper.map(accounts, AccountDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id)
        );

        if (accountDto.getAddress() != null) account.setAddress(accountDto.getAddress());
        if (accountDto.getCity() != null) account.setCity(accountDto.getCity());
        if (accountDto.getAccountNumber() != null) account.setAccountNumber(accountDto.getAccountNumber());
        if (accountDto.getCountry() != null) account.setCountry(accountDto.getCountry());
        if (accountDto.getDateOfBirth() != null) account.setDateOfBirth(accountDto.getDateOfBirth());
        if (accountDto.getGender() != null) account.setGender(accountDto.getGender());
        if (accountDto.getAccountType() != null) account.setAccountType(accountDto.getAccountType());

        Account savedAccount = accountRepository.save(account);
        return modelMapper.map(savedAccount, AccountDto.class);
    }

    @Transactional
    @Override
    public AccountDto depositInToAccount(Long id, Double amount) {

        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id)
        );

        if (amount == null || amount <= 0){
            throw new   IllegalArgumentException("Deposit amount must be greater than 0");
        }

        // update balance
      double currentBalance = (account.getBalance() != null) ? account.getBalance() : 0.0;
      account.setBalance(currentBalance + amount);
        // save transaction record

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType("DEPOSIT");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAccount(account);

        account.getTransactions().add(transaction);

        Account saveAccount =  accountRepository.save(account);

        return modelMapper.map(saveAccount, AccountDto.class);
    }

    @Transactional
    @Override
    public AccountDto withdrawFromAccount(Long id, Double amount) {

        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id)
        );

        if (amount == null || amount <= 0){
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0");
        }

        if (account.getBalance() < amount){
            throw new IllegalArgumentException("Insufficient balance");

        }

        // Deduct Balance
        account.setBalance(account.getBalance() - amount);

        //save Transaction record

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType("WITHDRAWAL");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAccount(account);

        account.getTransactions().add(transaction);

        Account savedAccount = accountRepository.save(account);

        return modelMapper.map(savedAccount, AccountDto.class);


    }

    @Override
    public AccountDto checkBalance(Long id) {

        Account checkBalance = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id)
        );

        if (checkBalance.getBalance() == null){
            checkBalance.setBalance(0.0);
        }

        return modelMapper.map(checkBalance, AccountDto.class);
    }


}
