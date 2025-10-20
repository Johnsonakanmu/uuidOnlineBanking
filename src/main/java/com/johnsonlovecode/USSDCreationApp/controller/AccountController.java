package com.johnsonlovecode.USSDCreationApp.controller;

import com.johnsonlovecode.USSDCreationApp.dto.AccountDto;
import com.johnsonlovecode.USSDCreationApp.dto.TransactionDto;
import com.johnsonlovecode.USSDCreationApp.entity.Account;
import com.johnsonlovecode.USSDCreationApp.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // for Swagger implementation For POST
    @Operation(
            summary = "Create Account Rest API",
            description = "Create account Rest API is used to save account in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    //

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto){

        AccountDto savedAccount =  accountService.createAccount(accountDto);

        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }


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
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") Long accountId){

        AccountDto account = accountService.getAccountById(accountId);

        return new ResponseEntity<>(account, HttpStatus.OK);
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
    public ResponseEntity<List<AccountDto>> getAllAccounts(){

       List<AccountDto> accountDto =  accountService.getAllAccounts();

       return ResponseEntity.ok(accountDto);

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

    @PatchMapping("/{id}/update")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable("id") Long accountId, @RequestBody AccountDto accountDto){

        AccountDto updateAccountDto = accountService.updateAccount(accountId, accountDto);

        return new ResponseEntity<>(updateAccountDto, HttpStatus.OK);

    }

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
    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> depositInToAccount(@PathVariable("id") Long accountId, @RequestBody TransactionDto request){

        AccountDto accountDto = accountService.depositInToAccount(accountId, request.getAmount());
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
    @PostMapping("/{id}/withdrawal")
    public ResponseEntity<AccountDto>  withdrawFromAccount(@PathVariable("id") Long accountId, @RequestBody TransactionDto request){

        AccountDto accountDto = accountService.withdrawFromAccount(accountId, request.getAmount());

        return new ResponseEntity<>(accountDto, HttpStatus.OK);

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
    @GetMapping("/{id}/balance")
    public ResponseEntity<Map<String, Object>> checkBalance(@PathVariable("id") Long accountId){

        AccountDto account = accountService.checkBalance(accountId);

        Map<String, Object> response = new HashMap<>();
        response.put("accountNumber", account.getAccountNumber());
        response.put("accountName", account.getFirstName() + " " + account.getLastName());
        response.put("balance", account.getBalance());

        return  new ResponseEntity<>(response, HttpStatus.OK);

    }

    // for Swagger implementation For Delete
    @Operation(
            summary = "Delete account Rest API",
            description = "Delete account Rest API is used to Delete a particular account in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long accountId){

        accountService.deleteAccount(accountId);
        return new  ResponseEntity<>("Account deleted Successfully", HttpStatus.OK);

    }

}
