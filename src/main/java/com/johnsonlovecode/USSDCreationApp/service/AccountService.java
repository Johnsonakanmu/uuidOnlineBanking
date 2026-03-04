package com.johnsonlovecode.USSDCreationApp.service;

import com.johnsonlovecode.USSDCreationApp.dto.*;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    public AccountResponseDto createAccount(AccountRequestDto dto);

    public AccountResponseDto getAccountById(Long id);

    public List<AccountResponseDto> getAllAccounts();

    public AccountResponseDto updateAccount(Long id, AccountUpdateRequestDto updateRequestDto);

    public AccountResponseDto depositInToAccount(Long id, BigDecimal amount);

    public AccountResponseDto withdrawFromAccount(Long id, BigDecimal amount);

    public BalanceResponseDto checkBalance(Long id);

    void deleteAccount(Long id);
}
