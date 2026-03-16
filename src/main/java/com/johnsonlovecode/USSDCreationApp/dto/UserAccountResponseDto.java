package com.johnsonlovecode.USSDCreationApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountResponseDto {

    private Long userId;
    private String fullName;
    private String email;
    private String phoneNumber;

    private List<AccountDto> accounts;
}
