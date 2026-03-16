package com.johnsonlovecode.USSDCreationApp.controller;

import com.johnsonlovecode.USSDCreationApp.dto.AccountRequestDto;
import com.johnsonlovecode.USSDCreationApp.dto.AccountResponseDto;
import com.johnsonlovecode.USSDCreationApp.service.AuthService;
import com.johnsonlovecode.USSDCreationApp.utils.AccountType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

// for Swagger implementation
@Tag(
        name = "CRUD REST APIs for UUID Online Banking Resource"
//        description = "CRUD REST APIs - Create Account , Update Account, Get Account, Get All Account, Deposit Account, Withdraw Account, Check Balance"
)

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminController {

    private AuthService authService;

    // for Swagger implementation For POST
    @Operation(
            summary = "Create Account/ Register Admin  Rest API",
            description = "Create account /register admin Rest API is used to save account in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    //

    @PostMapping("/createAccountAndRegisterAdminUser")
    public ResponseEntity<AccountResponseDto> createAccountAndRegisterAdminUser(
            @Valid @RequestBody AccountRequestDto accountRequestDto){

        AccountResponseDto savedAccount = authService.createAccountAndRegisterAdminUser(accountRequestDto);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities());

        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }


    // for Swagger implementation For POST
    @Operation(
            summary = "Create another account Type/ Register Admin  Rest API",
            description = "Create account /register admin Rest API is used to save account in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    //

    @PostMapping("/{userId}/open-account")
    public AccountResponseDto openAnotherAccount(
            @PathVariable("userId") Long userId,
            @RequestParam AccountType type,
            @RequestParam String pin) {

        return authService.openAnotherAccountForAdminUser(userId, type, pin);
    }



}
