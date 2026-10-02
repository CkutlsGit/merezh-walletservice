# Wallet Service - Merezh

Microservice responsible for managing user wallets and balances.

📖 In Russian: [перевод на русский](https://github.com/CkutlsGit/merezh-walletservice/blob/main/README.ru.md)

## 📋 Overview

Wallet Service is a Spring Boot microservice that manages user wallets: creation, balance retrieval, top-up, withdrawal, and deletion. It is used by Payment Service to debit funds during order processing.

The service stores only balances - it does not process payments itself. Payment Service calls Wallet Service to withdraw funds. The service trusts `X-User-Id` from the Gateway and uses pessimistic locking to prevent race conditions during balance operations.

## 🚀 Technology Stack

**Backend**

- Java 21 - core language
- Spring Boot 3 - application framework
- Spring Data JPA - database access and ORM
- RestTemplate - synchronous HTTP calls (for future integrations)

**Database**

- PostgreSQL - production database

**DevOps**

- Docker - containerization
- Docker Compose - multi-container orchestration
- Spring Boot Actuator - health checks and monitoring

**Testing**

- JUnit 5
- Mockito

## ✨ Features

### 👛 Wallet Management

- Create a wallet for a user (or auto-create on first operation)
- Get the current balance
- Top up the balance
- Withdraw funds with insufficient-funds check
- Delete a wallet

### 🔒 Concurrency Safety

- **Pessimistic locking** (`@Lock(PESSIMISTIC_WRITE)`) on balance operations
- Prevents double-spending under concurrent requests
- Atomic balance updates within a single transaction

### ✅ Data Validation

- Consistent error responses via `@RestControllerAdvice`
- Insufficient funds check before withdrawal

## 🛠️ Quick Start

### Prerequisites

- Docker
- Docker Compose

### Run with Docker Compose

```bash
docker compose up --build
```

The service will be available on port **8083**. 
Swagger path - `/swagger-ui.html`.

## 📚 API Endpoints

Base path: `/api/v1/wallets`

| Method | Endpoint          | Description                              | Access        |
|--------|-------------------|------------------------------------------|---------------|
| POST   | `/create`         | Create a wallet for the authenticated user | Authenticated |
| GET    | `/balance`        | Get the current user's balance           | Authenticated |
| POST   | `/balance/sum`    | Top up the balance                       | Authenticated |
| POST   | `/balance/sub`    | Withdraw funds                           | Authenticated / Payment Service |
| DELETE | `/delete/{id}`    | Delete a wallet by user ID               | ADMIN         |

**Note:** The service trusts the `X-User-Id` header set by the Gateway.

## 📦 Project Structure

```
src/main/java/ru/merezh/walletservice/
├── config/                    # Spring configuration
├── controller/                # REST controllers
├── dto/                       # Data Transfer Objects
├── entity/                    # JPA entities (Wallet)
├── exception/                 # Custom exceptions and handlers
│   ├── controller/            # @RestControllerAdvice
│   └── dto/                   # Error response DTOs
├── repository/                # Spring Data JPA repositories
└── service/                   # Business logic
```

## 🔒 Security

- **Wallet creation is triggered by the authenticated user** (`X-User-Id` from the Gateway).
- **Balance operations use pessimistic locking** to prevent race conditions.
- **Insufficient funds** trigger an exception before any modification.
- **The service trusts the Gateway** for user identity.
- **Internal endpoints** should not be exposed directly to the public - only through the Gateway.

## 🩺 Health Checks

The service exposes Spring Boot Actuator endpoints:

| Endpoint                     | Purpose                        |
|------------------------------|--------------------------------|
| `/actuator/health`           | Overall health                 |
| `/actuator/health/liveness`  | Liveness probe                 |
| `/actuator/health/readiness` | Readiness probe (includes DB)  |
| `/actuator/info`             | Service info                   |

## 🧪 Testing

```bash
mvn test
```

Unit tests cover the main `WalletService` flows:

- `sumWallet` with valid data → new balance
- `subWallet` with insufficient funds → exception
- `subWallet` with valid data → new balance
