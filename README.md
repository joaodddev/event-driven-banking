# Event-Driven Banking System

A microservices-based banking system built with Java 21, Spring Boot, Apache Kafka, and PostgreSQL. This project demonstrates event-driven architecture, asynchronous communication, and distributed systems design patterns.

## Architecture

```
HTTP Client
     │
     ▼
account-service (8080)
     │
     ├── REST ──► transfer-service (8081)
     │
     └── Kafka ──► account-events
                        │
transfer-service         │
     └── Kafka ──► transfer-events
                        │
                   notification-service (8082)
                        └── persists event history
```

## Microservices

| Service | Port | Database | Responsibility |
|---|---|---|---|
| account-service | 8080 | accounts-db:5432 | Account management, balances |
| transfer-service | 8081 | transfers-db:5433 | Transfers with idempotency |
| notification-service | 8082 | notifications-db:5434 | Event consumption and history |

## Tech Stack

- **Java 21**
- **Spring Boot 3.3**
- **Apache Kafka** — async event streaming
- **PostgreSQL 16** — isolated database per service
- **Flyway** — database migrations
- **Docker Compose** — local infrastructure
- **Lombok** — boilerplate reduction

## Key Features

- ✅ Event-driven architecture with Kafka
- ✅ Idempotent transfers (no duplicate processing)
- ✅ Database-per-service pattern
- ✅ Domain events (AccountCreated, TransferCompleted)
- ✅ Flyway migrations
- ✅ Dead Letter Queue and retry (in progress)
- ✅ Integration tests with Testcontainers (in progress)

### API Endpoints

**account-service** — `http://localhost:8080`

| Method | Endpoint | Description |
|---|---|---|
| POST | /accounts | Create account |
| GET | /accounts | List all accounts |
| GET | /accounts/{id} | Find by ID |
| GET | /accounts/cpf/{cpf} | Find by CPF |
| PATCH | /accounts/{id}/balance | Update balance |

**transfer-service** — `http://localhost:8081`

| Method | Endpoint | Description |
|---|---|---|
| POST | /transfers | Create transfer |
| GET | /transfers | List all transfers |
| GET | /transfers/{id} | Find by ID |

## Project Structure

```
event-driven-banking/
├── account-service/
├── transfer-service/
├── notification-service/
├── docker-compose.yml
└── pom.xml
```

## Author

João — [github.com/joaodddev](https://github.com/joaodddev)