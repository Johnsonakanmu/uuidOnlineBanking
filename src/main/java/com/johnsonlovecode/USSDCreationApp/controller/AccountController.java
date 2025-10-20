package com.johnsonlovecode.USSDCreationApp.controller;

import com.johnsonlovecode.USSDCreationApp.dto.AccountDto;
import com.johnsonlovecode.USSDCreationApp.dto.TransactionDto;
import com.johnsonlovecode.USSDCreationApp.entity.Account;
import com.johnsonlovecode.USSDCreationApp.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto){

        AccountDto savedAccount =  accountService.createAccount(accountDto);

        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") Long accountId){

        AccountDto account = accountService.getAccountById(accountId);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){

       List<AccountDto> accountDto =  accountService.getAllAccounts();

       return ResponseEntity.ok(accountDto);

    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable("id") Long accountId, @RequestBody AccountDto accountDto){

        AccountDto updateAccountDto = accountService.updateAccount(accountId, accountDto);

        return new ResponseEntity<>(updateAccountDto, HttpStatus.OK);

    }


    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> depositInToAccount(@PathVariable("id") Long accountId, @RequestBody TransactionDto request){

        AccountDto accountDto = accountService.depositInToAccount(accountId, request.getAmount());
        return  new ResponseEntity<>(accountDto, HttpStatus.CREATED);

    }


    @PostMapping("/{id}/withdrawal")
    public ResponseEntity<AccountDto>  withdrawFromAccount(@PathVariable("id") Long accountId, @RequestBody TransactionDto request){

        AccountDto accountDto = accountService.withdrawFromAccount(accountId, request.getAmount());

        return new ResponseEntity<>(accountDto, HttpStatus.OK);

    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Map<String, Object>> checkBalance(@PathVariable("id") Long accountId){

        AccountDto account = accountService.checkBalance(accountId);

        Map<String, Object> response = new HashMap<>();
        response.put("accountNumber", account.getAccountNumber());
        response.put("accountName", account.getFirstName() + " " + account.getLastName());
        response.put("balance", account.getBalance());

        return  new ResponseEntity<>(response, HttpStatus.OK);

    }

}
