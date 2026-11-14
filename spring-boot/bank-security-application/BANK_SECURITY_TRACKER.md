# Bank Security Application Development Tracker

## Repository

`backend-learning-journey`

## Project

`spring-boot/bank-security-application`

## Development Branch

`feature/bank-security-application`

## Final Status

**Implementation complete**

All specified project layers and endpoint groups have been implemented. The application was developed incrementally and validated through local startup checks and CodingNinjas platform submission.

---

## Completed Milestones

### Project Foundation

- [x] Initialize Spring Boot project
- [x] Configure Java 17
- [x] Use Spring Boot 2.7.16
- [x] Configure Maven dependencies
- [x] Establish feature-branch workflow

### Domain Layer

- [x] `AccountType`
- [x] `BranchType`
- [x] `CardType`
- [x] `InvestmentType`

### Entities

- [x] `Account`
- [x] `Card`
- [x] `Investment`
- [x] `Nominee`
- [x] `Role`
- [x] `User`
- [x] Configure entity relationships
- [x] Configure enum persistence
- [x] Configure explicit table names
- [x] Prevent recursive entity serialization

### DTO Layer

- [x] `AccountDto`
- [x] `AdminDto`
- [x] `CardDto`
- [x] `InvestmentDto`
- [x] `KycDto`
- [x] `NomineeDto`
- [x] `UserDto`
- [x] `JwtRequest`
- [x] `JwtResponse`

### Repository Layer

- [x] `AccountRepository`
- [x] `CardRepository`
- [x] `InvestmentRepository`
- [x] `NomineeRepository`
- [x] `UserRepository`
- [x] Account status filtering
- [x] Account-type filtering
- [x] Branch filtering
- [x] Account-number lookup
- [x] Card-number lookup
- [x] Username lookup

### Security

- [x] `CustomUserDetailService`
- [x] `JwtAuthenticationHelper`
- [x] `JwtAuthenticationFilter`
- [x] `SecurityConfig`
- [x] BCrypt password encoder
- [x] Authentication manager
- [x] JWT generation
- [x] JWT validation
- [x] Stateless session management
- [x] Public registration and login endpoints
- [x] Protected banking endpoints
- [x] `ROLE_CUSTOMER`
- [x] `ROLE_ADMIN`

### Service Layer

- [x] `UserService`
- [x] `AccountService`
- [x] `CardService`
- [x] `InvestmentService`
- [x] `AdminService`
- [x] Service implementation classes
- [x] User registration logic
- [x] Login and authentication logic
- [x] Account creation logic
- [x] Account query operations
- [x] Nominee and KYC maintenance
- [x] Card allocation and blocking
- [x] Card settings
- [x] Investment processing
- [x] Administrative operations

### Controllers

- [x] `UserController`
- [x] `UserAccountController`
- [x] `UserCardController`
- [x] `UserInvestmentController`
- [x] `AdminController`

### User Features

- [x] Register customer
- [x] Encode customer password
- [x] Assign `ROLE_CUSTOMER`
- [x] Authenticate user
- [x] Generate JWT

### Account Features

- [x] Create an account for an existing user
- [x] Generate account number
- [x] Set account status to `ACTIVE`
- [x] Apply account-type rules
- [x] Retrieve accounts by user
- [x] Retrieve balance
- [x] Retrieve nominee
- [x] Update nominee
- [x] Retrieve KYC details
- [x] Update KYC details
- [x] Retrieve account summary
- [x] Maintain the user-account relationship

### Card Features

- [x] Allocate cards during account creation
- [x] Generate card number
- [x] Generate CVV
- [x] Set allocation date
- [x] Set expiry date five years ahead
- [x] Apply for a new card
- [x] Prevent duplicate card allocation
- [x] Block a card
- [x] Update PIN
- [x] Validate daily transaction limits

### Investment Features

- [x] Validate investment amount
- [x] Reject investments exceeding account balance
- [x] Create investment
- [x] Associate investment with user
- [x] Deduct investment amount from account balance

### Administration

- [x] Register administrator
- [x] Encode administrator password
- [x] Assign `ROLE_ADMIN`
- [x] Retrieve all users
- [x] Retrieve user by username
- [x] Delete user
- [x] Activate account
- [x] Deactivate account
- [x] Retrieve active accounts
- [x] Retrieve inactive accounts
- [x] Filter accounts by account type
- [x] Filter accounts by branch

### Validation and Debugging

- [x] Verify application startup
- [x] Validate user registration locally
- [x] Validate H2-based development startup
- [x] Resolve reserved SQL table-name conflict
- [x] Resolve role persistence issue
- [x] Resolve JWT signing-key issue
- [x] Resolve recursive JSON serialization
- [x] Align resource response statuses
- [x] Refine entity relationship handling
- [x] Refine card operation behavior
- [x] Complete CodingNinjas platform submission

### Documentation and Git

- [x] Maintain README
- [x] Maintain development tracker
- [x] Use conventional commit messages
- [x] Keep controller and business-logic commits separate
- [x] Use a dedicated feature branch
- [x] Preserve incremental implementation history
- [ ] Merge completed feature branch into `main`

---

## Implementation Sequence Summary

1. Project initialization
2. Enums
3. Entities
4. DTOs
5. Repositories
6. JWT models
7. Custom user details service
8. Service interfaces and implementations
9. JWT helper and authentication filter
10. Spring Security configuration
11. User authentication endpoints and logic
12. Account endpoints and logic
13. Card endpoints and logic
14. Investment endpoint and logic
15. Admin endpoints and logic
16. Persistence and serialization fixes
17. Final relationship and card-operation refactoring
18. Documentation completion
19. Merge to `main`

---

## Development Principles

- Git history and current code were treated as the project source of truth.
- The CodingNinjas problem statement defined requirements and evaluator-sensitive contracts.
- Implementation order followed class dependencies rather than endpoint count.
- Required endpoint paths, method signatures, and DTO structures were preserved.
- Business logic was developed in small, meaningful commits.
- Platform-specific workarounds were separated from recruiter-friendly repository cleanup.
