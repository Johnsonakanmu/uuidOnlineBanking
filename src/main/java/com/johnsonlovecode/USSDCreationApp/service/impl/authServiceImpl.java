package com.johnsonlovecode.USSDCreationApp.service.impl;

import com.johnsonlovecode.USSDCreationApp.Jwt.JWTService;
import com.johnsonlovecode.USSDCreationApp.config.CustomUser;
import com.johnsonlovecode.USSDCreationApp.dto.*;
import com.johnsonlovecode.USSDCreationApp.entity.Account;
import com.johnsonlovecode.USSDCreationApp.entity.User;
import com.johnsonlovecode.USSDCreationApp.exception.ResourceNotFoundException;
import com.johnsonlovecode.USSDCreationApp.repository.AccountRepository;
import com.johnsonlovecode.USSDCreationApp.repository.UserRepository;
import com.johnsonlovecode.USSDCreationApp.service.AuthService;
import com.johnsonlovecode.USSDCreationApp.utils.AccountType;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class authServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private AccountRepository accountRepository;
    private JWTService jwtService;


    @Override
    public AccountResponseDto createAccountAndRegisterNormalUser(AccountRequestDto dto) {

        // Check for duplicate
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exist");
        }
        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exist");
        }

        // Create user
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Set roles
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");   // default role

        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        // Create account
        Account account = new Account();
        account.setPin(passwordEncoder.encode(dto.getPin()));
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setAddress(dto.getAddress());
        account.setCity(dto.getCity());
        account.setCountry(dto.getCountry());
        account.setDateOfBirth(dto.getDateOfBirth());
        account.setGender(dto.getGender());
        account.setUser(savedUser);
        if (dto.getTypes() != null) {
            try {
                AccountType type = AccountType.valueOf(dto.getTypes().toUpperCase());
                account.setTypes(type.name());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid account type. Must be SAVINGS, CURRENT, or FIXED.");
            }
        }

        Account savedAccount = accountRepository.save(account);

        // Prepare response
        AccountResponseDto response = new AccountResponseDto();
        response.setFullName(
                account.getUser().getFirstName() + " " +
                        account.getUser().getLastName()
        );
        response.setId(savedAccount.getId());
        response.setEmail(account.getUser().getEmail());
        response.setPhoneNumber(account.getUser().getPhoneNumber());
        response.setAccountNumber(savedAccount.getAccountNumber());
        response.setTypes(savedAccount.getTypes());
        response.setBalance(savedAccount.getBalance());
        response.setAuthorities(savedUser.getRoles());

        return response;
    }

    public AccountResponseDto createAccountAndRegisterAdminUser(AccountRequestDto dto) {

        // Check for duplicate
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exist");
        }
        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exist");
        }

        // Create user
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Set roles
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");   // default role
        roles.add("ROLE_ADMIN");

        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        // Create account
        Account account = new Account();
        account.setPin(passwordEncoder.encode(dto.getPin()));
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setAddress(dto.getAddress());
        account.setCity(dto.getCity());
        account.setCountry(dto.getCountry());
        account.setDateOfBirth(dto.getDateOfBirth());
        account.setGender(dto.getGender());
        account.setUser(savedUser);
        if (dto.getTypes() != null) {
            try {
                AccountType type = AccountType.valueOf(dto.getTypes().toUpperCase());
                account.setTypes(type.name());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid account type. Must be SAVINGS, CURRENT, or FIXED.");
            }
        }

        Account savedAccount = accountRepository.save(account);

        // Prepare response
        AccountResponseDto response = new AccountResponseDto();
        response.setFullName(
                account.getUser().getFirstName() + " " +
                        account.getUser().getLastName()
        );
        response.setId(savedAccount.getId());
        response.setEmail(account.getUser().getEmail());
        response.setPhoneNumber(account.getUser().getPhoneNumber());
        response.setAccountNumber(savedAccount.getAccountNumber());
        response.setTypes(savedAccount.getTypes());
        response.setBalance(savedAccount.getBalance());
        response.setAuthorities(savedUser.getRoles());

        return response;
    }

    private String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.valueOf((long)(Math.random() * 1_000_000_0000L));
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        String token = jwtService.generateToken(new CustomUser(user));

        List<Account> accountDto = accountRepository.findByUserId(user.getId());

        List<AccountSummaryDto> accounts = accountDto.stream()
                .map(account -> new AccountSummaryDto(
                        account.getAccountNumber(),
                        account.getTypes().toUpperCase(),
                        account.getBalance()
                )).toList();

        LoginResponseDto response = new LoginResponseDto();

        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setToken(token);
        response.setAccounts(accounts);
        response.setRoles(user.getRoles());
        return response;
    }


    @Override
    public AccountResponseDto openAnotherAccountForNormalUser(Long userId, AccountType type, String pin) {

        // Validate PIN
        if (pin == null || pin.trim().isEmpty()) {
            throw new IllegalArgumentException("PIN cannot be null or empty");
        }

        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Check if user already has this account type
        boolean exists = accountRepository.existsByUserIdAndTypes(userId, type.name());

        if (exists) {
            throw new IllegalStateException("User already has this account type");
        }

        // Create new account
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setTypes(type.name());
        account.setBalance(BigDecimal.ZERO);
        account.setPin(passwordEncoder.encode(pin));
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);

        // Map to response DTO
        AccountResponseDto response = new AccountResponseDto();
        response.setId(savedAccount.getId());
        response.setAccountNumber(savedAccount.getAccountNumber());
        response.setTypes(savedAccount.getTypes());
        response.setBalance(savedAccount.getBalance());

        // User info
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());

        // Account details
        response.setAddress(savedAccount.getAddress());
        response.setCity(savedAccount.getCity());
        response.setCountry(savedAccount.getCountry());
        response.setGender(savedAccount.getGender());

        return response;
    }

    @Override
    public AccountResponseDto openAnotherAccountForAdminUser(Long userId, AccountType type, String pin) {
        // Validate PIN
        if (pin == null || pin.trim().isEmpty()) {
            throw new IllegalArgumentException("PIN cannot be null or empty");
        }

        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Check if user already has this account type
        boolean exists = accountRepository.existsByUserIdAndTypes(userId, type.name());

        if (exists) {
            throw new IllegalStateException("User already has this account type");
        }

        // Create new account
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setTypes(type.name());
        account.setBalance(BigDecimal.ZERO);
        account.setPin(passwordEncoder.encode(pin));
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);

        // Map to response DTO
        AccountResponseDto response = new AccountResponseDto();
        response.setId(savedAccount.getId());
        response.setAccountNumber(savedAccount.getAccountNumber());
        response.setTypes(savedAccount.getTypes());
        response.setBalance(savedAccount.getBalance());

        // User info
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());

        // Account details
        response.setAddress(savedAccount.getAddress());
        response.setCity(savedAccount.getCity());
        response.setCountry(savedAccount.getCountry());
        response.setGender(savedAccount.getGender());

        return response;
    }

}



