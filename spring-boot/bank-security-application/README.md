# Bank Security Application

A Spring Boot REST API that simulates core banking services, including user authentication, account management, card operations, investments, KYC updates, nominee management, and administrative account controls.

This mini-project was developed as part of the CodingNinjas Java Spring Boot Web Development course. Development followed an incremental feature-branch workflow with meaningful Git commits.

## Tech Stack

- Java 17
- Spring Boot 2.7.16
- Spring Security
- JSON Web Tokens (JWT)
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok

## Features

### Authentication and Security

- Customer and administrator registration
- BCrypt password encoding
- Username and password authentication
- JWT generation and validation
- Stateless Spring Security configuration
- Public access limited to registration and login
- Protected banking and administrative endpoints
- Role-based authorities:
  - `ROLE_CUSTOMER`
  - `ROLE_ADMIN`

### User Management

- Register customers with `ROLE_CUSTOMER`
- Register administrators with `ROLE_ADMIN`
- Retrieve all users
- Retrieve a user by username
- Delete users

### Account Management

- Create accounts for existing users
- Generate account numbers during account creation
- Set account status to `ACTIVE` during creation
- Retrieve every account belonging to a user
- Retrieve an account balance
- Retrieve and update nominee details
- Retrieve and update KYC details
- Retrieve an account summary
- Activate and deactivate accounts

Supported account types:

- `SAVINGS`
- `CURRENT`
- `PPF`
- `SALARY`

Supported branches:

- `SBI`
- `ICIC`
- `BOB`
- `HDFC`

### Card Management

- Allocate cards based on account type
- Generate card numbers and CVV values
- Set allocation and expiry dates
- Apply for a new card when an account has no linked card
- Block a card associated with an account
- Update card PIN and daily transaction limits

Supported card types:

- `DEBIT_CLASSIC`
- `DEBIT_GLOBAL`
- `CREDIT_PREMIUM`
- `CREDIT_MASTER`

### Investment Management

- Create investments from a bank account
- Validate the investment amount against the available account balance
- Deduct the invested amount from the account balance
- Associate investments with the account holder

Supported investment types:

- `GOLD`
- `STOCKS`
- `MUTUAL_FUND`
- `FIXED_DEPOSITS`

### Administrative Operations

- Register administrators
- Retrieve and delete users
- Activate or deactivate user accounts
- Retrieve active and inactive accounts
- Filter accounts by account type
- Filter accounts by branch

## Account Configuration Rules

| Account Type | Branch | Interest Rate | Allocated Card | Initial Daily Limit |
|---|---|---:|---|---:|
| `SAVINGS` | `BOB` | 2.7% | `DEBIT_GLOBAL` | 40,000 |
| `CURRENT` | `ICIC` | 5.2% | `CREDIT_PREMIUM` | 50,000 |
| `PPF` | `SBI` | 7.4% | None | Not applicable |
| `SALARY` | `HDFC` | 4.1% | `CREDIT_MASTER` | 75,000 |

## API Groups

| Base Path | Purpose |
|---|---|
| `/user` | Customer registration and login |
| `/account` | Account, nominee, balance, and KYC operations |
| `/card` | Card allocation, blocking, and settings |
| `/invest` | Investment operations |
| `/admin` | User administration and account controls |

## Project Structure

```text
src/main/java/com/security/bank
├── accounts
├── admin
├── cards
├── config
├── dto
├── entity
├── investments
├── jwt
├── repository
├── security
├── service
└── user
```

## Configuration

The application is designed for MySQL.

Configure the datasource in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/bank_application?createDatabaseIfNotExist=true
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
```

Set `DB_USERNAME` and `DB_PASSWORD` as environment variables rather than committing real database credentials.

## Running the Application

From the project directory:

```bash
mvn spring-boot:run
```

Alternatively, run `BankApplication` from Eclipse as a Java or Spring Boot application.

The API starts at:

```text
http://localhost:8080
```

## Development Workflow

This project was developed using:

- A dedicated feature branch
- Conventional commit messages
- Small, incremental implementation commits
- Separate controller and business-logic milestones
- Runtime debugging and evaluator-driven validation
- A final merge into the default branch after completion

## Project Status

**Completed**

The application includes all required entities, DTOs, repositories, security infrastructure, services, controllers, and banking operations defined by the mini-project specification.

Development history and implementation milestones are recorded in `BANK_SECURITY_TRACKER.md`.

## Learning Outcomes

This project provided practical experience with:

- Designing REST APIs with Spring Boot
- Implementing JWT-based stateless authentication
- Configuring Spring Security
- Mapping JPA entity relationships
- Managing bidirectional entity associations
- Writing Spring Data JPA repositories
- Implementing controller, service, and repository layers
- Debugging serialization and persistence issues
- Using Git feature branches and meaningful commits
