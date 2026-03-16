# UUID Online Banking System

## Introduction

This project is an Online Banking System built with Spring Boot. It provides core banking functionalities that allow users to register, create accounts, and perform financial transactions securely.

The system enables users to create accounts, perform deposits and withdrawals, retrieve accounts by ID, view all accounts, update account information, and check account balances. It also supports creating multiple account types for a single user, such as Savings, Current, and Fixed accounts.

Additionally, the system allows retrieval of users with their associated multiple accounts. All transactions are tracked using UUID identifiers, ensuring each transaction is uniquely identifiable and traceable within the system.


## Technologies Used
- Spring Boot
- Spring Security
- JWT Authentication
- MySQL
- JPA / Hibernate

## Features
- User Registration and Create Bank Account
- User Login
- Open Another Account
- Get User Accounts
- Get Account By Id
- Get All Accounts
- Update Account
- Deposit Money
- Withdraw Money
- Check Balance
- Delete Account
- Transaction History

### User Registration and Create Bank Account
This endpoint registers a new user and automatically generates a bank account for that user.

### Open Another Account
This endpoint allows an existing user to open an additional bank account with a different account type (e.g., Savings, Current, or Fixed). The system first verifies that the user exists and checks whether the user already has the specified account type.

### GetUser Accounts

This endpoint retrieves a user along with all the bank accounts associated with that user. The system first verifies that the user exists using the provided user ID.

###  GetAccount By Id
This endpoint retrieves the details of a specific bank account using its unique ID.

### Get All Accounts
This endpoint retrieves a list of all bank accounts in the system. For each account, the system returns the account details along with the associated user's basic information, including full name, email, and phone number

### Update Account
This endpoint allows updating the details of an existing bank account using its unique ID. The system first checks if the account exists in the database. If the account is not found, a ResourceNotFoundException is thrown.

### Deposit Money
This endpoint allows a user to deposit money into an existing bank account using the account ID. The system first verifies that the account exists and validates that the deposit amount is greater than zero.

### Withdraw Money
This endpoint allows a user to withdraw money from an existing bank account using the account ID. The system first verifies that the account exists and checks that the withdrawal amount is greater than zero.

### Check Balance
This endpoint allows a user to check the current balance of a specific bank account using the account ID

### Delete Account
This endpoint allows for the deletion of a bank account using its ID. The system first checks if the account exists in the database.


#### Technology use in build the project
### Spring boot
### Springdoc OpenAPI / Swagger UI
### Spring Security
### JSON Web Token (JWT)
### Hibernate / Java Persistence API
### MySQL
### Maven
