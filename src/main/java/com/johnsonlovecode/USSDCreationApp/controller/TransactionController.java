package com.johnsonlovecode.USSDCreationApp.controller;

import com.johnsonlovecode.USSDCreationApp.dto.*;
import com.johnsonlovecode.USSDCreationApp.service.AccountService;
import com.johnsonlovecode.USSDCreationApp.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

// for Swagger implementation
@Tag(
        name = "CRUD REST APIs for UUID Online Banking Resource"
//        description = "CRUD REST APIs - Create Account , Update Account, Get Account, Get All Account, Deposit Account, Withdraw Account, Check Balance"
)


@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {

    private AccountService accountService;
    private TransactionService transactionService;

    // for Swagger implementation For POST
    @Operation(
            summary = "Deposit funds into an account",
            description = "Deposits a specific amount into an existing account by its ID"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    //
    @PostMapping("/deposit")
    public ResponseEntity<AccountResponseDto> depositInToAccount(@RequestBody DepositRequestDto request){

        AccountResponseDto accountDto = accountService.depositInToAccount(request);
        return  new ResponseEntity<>(accountDto, HttpStatus.CREATED);

    }
    // for Swagger implementation For POST
    @Operation(
            summary = "Withdraw funds into an account",
            description = "Withdraw a specific amount into an existing account by its ID"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    //
    @PostMapping("/withdrawal")
    public ResponseEntity<AccountResponseDto>  withdrawFromAccount(@RequestBody WithdrawRequestDto request){

        AccountResponseDto response = accountService.withdrawFromAccount(request);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // for Swagger implementation For GET a Single User
    @Operation(
            summary = "Get account balance By ID Rest API",
            description = "Get account balance By ID Rest API is used to get a single account from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //
    @PostMapping("/balance")
    public ResponseEntity<BalanceResponseDto> checkBalance(@RequestBody BalanceRequestDto requestDto){

        BalanceResponseDto account = accountService.checkBalance(requestDto);

        return  new ResponseEntity<>(account, HttpStatus.OK);
    }

    // for Swagger implementation For GET a Single User
    @Operation(
            summary = "Get Transaction History Rest API",
            description = "Get Transaction History Rest API is used to get all translation  from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //

    @GetMapping("/{accountId}/transactions")
    public List<TransactionResponseDto> getTransactionHistory(@PathVariable Long accountId) {
        return transactionService.getTransactionHistory(accountId);
    }

    // for Swagger implementation For GET a Single User
    @Operation(
            summary = "Get the first 10 Transaction History Rest API",
            description = "Get the firs 10 Transaction History Rest API from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //
    @GetMapping("/{accountId}/last10")
    public List<TransactionResponseDto> getLast10Transactions(@PathVariable("accountId") Long accountId) {
        return transactionService.getLast10Transactions(accountId);
    }


    // for Swagger implementation For GET a Single User
    @Operation(
            summary = "Get Monthly Statement Transaction History Rest API",
            description = "Get Monthly Statement Transaction History Rest API from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //

    @GetMapping("/{accountId}/monthly")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<TransactionResponseDto> getTransactionsByDateRange(
            @PathVariable("accountId") Long accountId,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end   = to.atTime(LocalTime.MAX);

        return transactionService.getTransactionsByDateRange(accountId, start, end);
    }

    // for Swagger implementation For GET a Single User
    @Operation(
            summary = "Get Filter Transaction History Rest API",
            description = "Get Monthly Statement Transaction History Rest API from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //

    @GetMapping("/{accountId}/filter")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<TransactionResponseDto> filterTransactions(
            @PathVariable("accountId") Long accountId,
            @RequestParam String  type) {

        return transactionService.filterTransactionsByType(accountId, type);
    }





}
