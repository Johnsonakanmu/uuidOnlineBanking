package com.johnsonlovecode.USSDCreationApp.service;

import com.johnsonlovecode.USSDCreationApp.dto.AccountRequestDto;
import com.johnsonlovecode.USSDCreationApp.dto.AccountResponseDto;
import com.johnsonlovecode.USSDCreationApp.dto.LoginRequestDto;
import com.johnsonlovecode.USSDCreationApp.dto.LoginResponseDto;
import com.johnsonlovecode.USSDCreationApp.utils.AccountType;

public interface AuthService {

    public LoginResponseDto login(LoginRequestDto request);

    public AccountResponseDto createAccountAndRegisterNormalUser(AccountRequestDto dto);

    public AccountResponseDto createAccountAndRegisterAdminUser(AccountRequestDto dto);

    public AccountResponseDto openAnotherAccountForNormalUser(Long id, AccountType types, String pin);

    public AccountResponseDto openAnotherAccountForAdminUser(Long id, AccountType types, String pin);



}
