package com.johnsonlovecode.USSDCreationApp.controller;

import com.johnsonlovecode.USSDCreationApp.dto.AccountResponseDto;
import com.johnsonlovecode.USSDCreationApp.dto.OpenAccountRequestDto;
import com.johnsonlovecode.USSDCreationApp.dto.UserAccountResponseDto;
import com.johnsonlovecode.USSDCreationApp.service.AccountService;
import com.johnsonlovecode.USSDCreationApp.service.AuthService;
import com.johnsonlovecode.USSDCreationApp.utils.AccountType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// for Swagger implementation
@Tag(
        name = "CRUD REST APIs for UUID Online Banking Resource"
//        description = "CRUD REST APIs - Create Account , Update Account, Get Account, Get All Account, Deposit Account, Withdraw Account, Check Balance"
)


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private AccountService accountService;


    // for Swagger implementation For GET a Single User
    @Operation(
            summary = "Get user with multiple account By ID Rest API",
            description = "Get user with multiple account By ID Rest API is used to get a single user with multiple account the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //

    @GetMapping("/{id}/accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<UserAccountResponseDto> getUserWithMultipleAccountTypes(@PathVariable("id") Long userId){
        return ResponseEntity.ok(accountService.getUserWithMultipleAccountTypes(userId));

    }



}
