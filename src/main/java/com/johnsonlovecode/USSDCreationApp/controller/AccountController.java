package com.johnsonlovecode.USSDCreationApp.controller;

import com.johnsonlovecode.USSDCreationApp.dto.*;
import com.johnsonlovecode.USSDCreationApp.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// for Swagger implementation
@Tag(
        name = "CRUD REST APIs for UUID Online Banking Resource"
//        description = "CRUD REST APIs - Create Account , Update Account, Get Account, Get All Account, Deposit Account, Withdraw Account, Check Balance"
)

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;


    // for Swagger implementation For GET a Single User
    @Operation(
            summary = "Get account By ID Rest API",
            description = "Get account By ID Rest API is used to get a single account from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(
            @PathVariable Long id) {

        AccountResponseDto account = accountService.getAccountById(id);

        return ResponseEntity.ok(account);
    }

    // for Swagger implementation For GET all User
    @Operation(
            summary = "GET All account Rest API",
            description = "Get All accounts Rest API is used to get all account from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {

        List<AccountResponseDto> accounts = accountService.getAllAccounts();

        return ResponseEntity.ok(accounts);
    }

    // for Swagger implementation For UPDATE
    @Operation(
            summary = "Update account Rest API",
            description = "Update account Rest API is used to update a particular account user in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountResponseDto> updateAccount(
            @PathVariable Long id,
            @RequestBody AccountUpdateRequestDto dto) {

        AccountResponseDto updatedAccount = accountService.updateAccount(id, dto);

        return ResponseEntity.ok(updatedAccount);
    }

    // for Swagger implementation For Delete
    @Operation(
            summary = "Delete account Rest API",
            description = "Delete account Rest API is used to Delete a particular account  in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long accountId){

        accountService.deleteAccount(accountId);
        return new  ResponseEntity<>("Account deleted Successfully", HttpStatus.OK);

    }

}
