# Task Manager API - Spring Boot

## Overview

This project is a production-style backend service built using Spring Boot.

It demonstrates how a real-world backend application is structured, including layered architecture, database integration, validation, and exception handling.

---

## What This Project Demonstrates

- Building a full CRUD REST API  
- Structuring backend using layered architecture  
- Integrating PostgreSQL using JPA/Hibernate  
- Using DTO pattern for clean API design  
- Implementing validation using Jakarta annotations  
- Handling errors using global exception handling  

---

## Architecture

Controller → Service → Repository → Database  

### Why this structure?

- Controller handles HTTP requests  
- Service contains business logic  
- Repository interacts with database  

This separation ensures:

- Clean code  
- Maintainability  
- Testability  

---

## DTO (Data Transfer Object)

DTO is used to transfer data between client and backend.

### Types used:

- Request DTO → Validates incoming data  
- Response DTO → Controls outgoing data  

### Why not return Entity?

- Prevents exposing database structure  
- Avoids tight coupling  
- Improves security and flexibility  

---

## Validation

Implemented using Jakarta annotations:

- `@NotBlank` → Prevents empty values  
- `@Size` → Ensures minimum length  

### Key Decision

Validation is applied on DTO instead of Entity.

### Why?

- Keeps validation at API boundary  
- Separates persistence from input validation  

---

## Global Exception Handling

Implemented using `@RestControllerAdvice`

### Handles:

- Validation errors (400 Bad Request)  
- Resource not found (404 Not Found)  

### Why?

- Centralized error handling  
- Consistent error responses  
- Cleaner controller code  

---

## JPA + Hibernate

- Entity maps Java object to database table  
- Repository extends `JpaRepository`  
- Provides built-in CRUD operations  

### Benefit:

- No need to write SQL manually  
- Faster development  

---

## ID Type Decision

Used `Long` instead of `int`

### Why?

- Matches database `BIGINT`  
- Prevents overflow issues  
- Ensures consistency across layers  

---

## API Endpoints

POST /tasks → Create task  
GET /tasks → Get all tasks  
GET /tasks/{id} → Get task by ID  
PUT /tasks/{id} → Update task  
DELETE /tasks/{id} → Delete task  

---

## Request Flow

Client  
→ DispatcherServlet  
→ Controller  
→ Service  
→ Repository  
→ Database  

Response flows back in reverse order.

---

## Key Improvements Made

- Introduced DTO pattern  
- Moved validation from Entity to DTO  
- Added global exception handling  
- Refactored ID type to Long  
- Standardized API responses  

---

## Real-World Learnings

- Never expose Entity directly in APIs  
- Always validate input at API level  
- Use exception handling instead of scattered error checks  
- Keep controller thin and focused  

---

## Connection to Core Concepts

This project reinforces:

- Spring Boot request lifecycle  
- Layered architecture  
- Clean code principles  
- API design best practices  

---

## Summary

This project demonstrates how to build a structured, maintainable, and production-ready backend service using Spring Boot.