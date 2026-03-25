# Task Manager API - Spring Boot

## What I Learned

- How to build a full CRUD REST API
- How to structure backend using Controller → Service → Repository
- How to integrate PostgreSQL with Spring Boot
- How to use JPA/Hibernate for database operations
- How to implement DTO pattern (Request + Response)
- How to handle validation using Jakarta annotations
- How to implement global exception handling

---

## Key Concepts

### Layered Architecture

Controller → Handles HTTP requests  
Service → Business logic  
Repository → Database interaction  

---

### DTO (Data Transfer Object)

- Used to transfer data between client and backend  
- Request DTO → Input validation  
- Response DTO → Controlled output  

---

### Validation

- Implemented using:
  - @NotBlank
  - @Size

- Applied on DTO (not Entity)

---

### Global Exception Handling

- Implemented using @RestControllerAdvice  
- Handles validation errors  
- Returns HTTP 400 with meaningful message  

---

### JPA + Hibernate

- Entity maps Java object to database table  
- Repository extends JpaRepository  
- No need to write SQL manually  

---

### ID Type Consistency

- Used Long instead of int  
- Matches database BIGINT type  
- Ensures consistency across layers  

---

## Endpoints

POST /tasks → Create task  
GET /tasks → Get all tasks  
GET /tasks/{id} → Get task by ID  
PUT /tasks/{id} → Update task  
DELETE /tasks/{id} → Delete task  

---

## Request Flow

Client → DispatcherServlet → Controller → Service → Repository → Database  
Database → Repository → Service → Controller → Response  

---

## Improvements Made

- Introduced DTO pattern  
- Moved validation from Entity to DTO  
- Added global exception handling  
- Refactored ID type to Long  
- Ensured consistent API responses  

---

## Notes

- Avoid returning Entity directly  
- Always validate input using DTO  
- Use global exception handling for clean error responses  
- Maintain consistency across all layers  
  
## Connection to Request Flow  
  
This project implements the Spring Boot request lifecycle:  
  
Client → Tomcat → DispatcherServlet → Controller → Service → Repository → Database → Response  
  
This reinforces concepts from:  
- spring-boot-request-flow.md  
- backend-core-concepts.md  
  
## Design Decisions  
  
- Used DTO instead of Entity to decouple API from database  
- Kept Controller thin to follow best practices  
- Centralized validation using annotations  
- Used global exception handling for consistent error responses  