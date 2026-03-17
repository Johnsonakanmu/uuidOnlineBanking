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
- Springdoc OpenAPI / Swagger UI
- Maven
- JPA / Hibernate

## Features
- Create Account and Register Normal User
- User Authentication & Account Summary Service (JWT-Based Login)
- Create Account and Register Admin User
- Open Additional Account for Existing User
- Open Additional Account for Admin User
- Get User Accounts
- Get Account By Id
- Get All Accounts
- Update Account
- Deposit Money
- Withdraw Money
- Check Balance
- Delete Account
- Get Account Transaction History
- Retrieve Last 10 Transactions for an Account
- Get Transactions by Date Range
- Filter Transactions by Type

### Create Account and Register Normal User
This endpoint handles both user registration and bank account creation for a normal user in a single operation.
Upon successful validation, a new user is created with encrypted credentials and assigned the default role of ROLE_USER.The account type is validated against predefined values (SAVINGS, CURRENT, FIXED) to ensure data integrity.
### Create Account and Register Admin User
This endpoint handles the creation of a new admin user along with their associated bank account in a single operation.
It first validates the uniqueness of the user's email and phone number to prevent duplicate registrations. Upon successful validation, a new user is created with securely encoded credentials and assigned both USER and ADMIN roles.

### User Authentication & Account Summary Service (JWT-Based Login)
This service handles user authentication and returns a secure login response using JWT (JSON Web Token). It validates user credentials, generates an authentication token, and provides a summarized view of the user’s associated bank accounts.

### Open Additional Account for Existing User
This feature allows an existing user to create an additional bank account of a specified type within the system.
The service validates the provided PIN, ensures the user exists, and checks that the user does not already own an account of the requested type

### Open Additional Account for Admin User
This endpoint allows an administrator to create an additional bank account for an existing user in the system.

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

### Get Account Transaction History
Retrieves the complete transaction history associated with a specific account. This endpoint fetches all transactions linked to the provided account ID and returns a list of transaction details, including the transaction ID, amount, type (e.g., debit or credit), transaction date, and the associated account number.

### Retrieve Last 10 Transactions for an Account
This method fetches the 10 most recent transactions associated with a given account ID. It queries the database to retrieve transactions in descending order of transaction date, ensuring the latest activities appear first. Each transaction is mapped to a TransactionResponseDto, which includes the transaction ID, amount, type, date, and the related account number.

### Get Transactions by Date Range
Retrieves a list of transactions for a specific account within a given date range. Each transaction is returned as a TransactionResponseDto containing the transaction ID, amount, type, transaction date, and the associated account number. This method is useful for generating account statements or filtering transaction history by specific time periods.

### Filter Transactions by Type
This method retrieves all transactions of a specific type (e.g., "DEPOSIT", "WITHDRAWAL") for a given account. It queries the database using the account ID and transaction type, then maps each transaction to a TransactionResponseDto containing key details such as transaction ID, amount, type, date, and the associated account number.