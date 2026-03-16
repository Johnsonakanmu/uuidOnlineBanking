package com.johnsonlovecode.USSDCreationApp.controller;

import com.johnsonlovecode.USSDCreationApp.dto.*;
import com.johnsonlovecode.USSDCreationApp.service.AuthService;
import com.johnsonlovecode.USSDCreationApp.utils.AccountType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// for Swagger implementation
@Tag(
        name = "CRUD REST APIs for UUID Online Banking Resource"
//        description = "CRUD REST APIs - Create Account , Update Account, Get Account, Get All Account, Deposit Account, Withdraw Account, Check Balance"
)

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    // for Swagger implementation For POST
    @Operation(
            summary = "Create Account/register user Rest API",
            description = "Create account / register user Rest API is used to save account in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    //

    @PostMapping("/createAccountAndRegisterNormalUser")
    public ResponseEntity<AccountResponseDto> createAccountAndRegisterNormalUser(@Valid @RequestBody AccountRequestDto accountRequestDto){

        AccountResponseDto savedAccount =  authService.createAccountAndRegisterNormalUser(accountRequestDto);

        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }


    // for Swagger implementation For POST
    @Operation(
            summary = "Login User Rest API",
            description = "Login User Rest API is used to login in a user"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    //

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){

        System.out.println("Login endpoint called");

        LoginResponseDto loginUser = authService.login(loginRequestDto);

        return ResponseEntity.ok(loginUser);
    }


    // for Swagger implementation For POST
    @Operation(
            summary = "Create another account types/ for Normal user  for an existing user.",
            description = "REST API that allows a client who already has a Savings account to create another account type (e.g., Fixed account) under the same user profile."
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    // openAnotherAccount

    @PostMapping("/{userId}/accounts")
    public AccountResponseDto openAnotherAccount(
            @PathVariable("userId") Long userId,
            @RequestParam AccountType type,
            @RequestParam String pin) {

        return authService.openAnotherAccountForNormalUser(userId, type, pin);
    }


}
