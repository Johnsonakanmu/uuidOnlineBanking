package com.johnsonlovecode.USSDCreationApp.service;

import com.johnsonlovecode.USSDCreationApp.dto.*;
import com.johnsonlovecode.USSDCreationApp.utils.AccountType;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {



    public AccountResponseDto getAccountById(Long id);

    public UserAccountResponseDto getUserWithMultipleAccountTypes(Long id);

    public List<AccountResponseDto> getAllAccounts();

    public AccountResponseDto updateAccount(Long id, AccountUpdateRequestDto updateRequestDto);

    public AccountResponseDto depositInToAccount(DepositRequestDto request);

//    @Transactional
//    AccountResponseDto depositInToAccount(DepositRequestDto request);

    public AccountResponseDto withdrawFromAccount(WithdrawRequestDto request);

    public BalanceResponseDto checkBalance(BalanceRequestDto request);

    public List<AccountResponseDto> searchAccountByName(String name);

    void deleteAccount(Long id);


}
