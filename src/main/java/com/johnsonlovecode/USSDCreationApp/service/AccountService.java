package com.johnsonlovecode.USSDCreationApp.service;

import com.johnsonlovecode.USSDCreationApp.dto.AccountDto;

import java.util.List;

public interface AccountService {

    public AccountDto createAccount(AccountDto accountDto);

    public AccountDto getAccountById(Long id);

    public List<AccountDto> getAllAccounts();

    public AccountDto updateAccount(Long id, AccountDto accountDto);

    public AccountDto depositInToAccount(Long id, Double amount);

    public AccountDto withdrawFromAccount(Long id, Double amount);

    public AccountDto checkBalance(Long id);

    void deleteAccount(Long id);
}
