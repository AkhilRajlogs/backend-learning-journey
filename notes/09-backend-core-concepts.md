# Backend Core Concepts

## Overview

This document connects all the core concepts required to understand how a backend system works end-to-end.

---

## 1. Java + Build System Basics

### JAR (Java Archive)

- A `.jar` file is a packaged Java application or library  
- Similar to a `.zip` file containing:  
  - `.class` files (compiled Java code)  
  - Metadata  

💡 Example: Spring Boot dependencies are downloaded as JAR files

---

### Classpath

- Classpath defines where Java looks for classes and libraries  

Without Maven:
- You manually manage classpath  

With Maven:
- Dependencies are automatically added to classpath  

💡 Flow:  
Dependency → JAR downloaded → Added to classpath → Available in code

---

## 2. Backend / Web Basics

### REST API

- REST API allows communication between client and backend  

Examples:
- GET /tasks  
- POST /tasks  
- PUT /tasks/1  
- DELETE /tasks/1  

---

### Tomcat Server

- Embedded web server in Spring Boot  

When you run:

    mvn spring-boot:run

- Tomcat starts automatically on port 8080  

💡 Flow:  
Client → Tomcat → Spring Boot → Response  

---

### @RestController

- Marks class as HTTP request handler  
- Returns data (usually JSON)  

---

### JSON Handling

- JSON is the data format used in APIs  

Example:

    {
      "id": 1,
      "title": "Learn Spring Boot",
      "completed": false
    }

Spring Boot automatically:
- Converts Java → JSON (response)  
- Converts JSON → Java (request)  

---

## 3. Database + Persistence

### SQL

- Language to interact with relational databases  

Basic operations:
- SELECT  
- INSERT  
- UPDATE  
- DELETE  

---

### PostgreSQL

- A relational database system  

💡 Flow:  
Java App → PostgreSQL → Data stored in tables  

---

### JPA (Java Persistence API)

- Standard way to interact with databases in Java  

Instead of writing SQL:

    SELECT * FROM tasks;

You write:

    taskRepository.findAll();

---

### Hibernate (ORM)

- Implementation of JPA  

---

### ORM (Object Relational Mapping)

- Converts Java objects ↔ database tables  

❌ Without ORM:
- Manual conversion between Java and SQL  

✅ With ORM:
- Automatic mapping  

Example:

Java Object:

    Task {
      id = 1  
      title = "Learn"  
    }

Database Table:

| id | title |
|----|-------|
| 1  | Learn |

---

## 4. Layers in My Project

    Controller → Service → Repository → Database

- Controller: Handles HTTP requests/responses  
- Service: Contains business logic  
- Repository: Handles database operations using JPA  

💡 Example from my project:

- Controller: TaskController  
- Service: TaskService  
- Repository: TaskRepository  

---

## 5. DTO (Data Transfer Object)

- DTO is used to transfer data between client and backend  

Why used:

- Prevent exposing database structure  
- Control request/response format  
- Improve security and flexibility  

Flow:

    Client → DTO → Service → Entity → Database  

---

## 6. Validation and Error Handling

### Validation

- Ensures only valid data enters the system  

Examples:

- @NotBlank → field cannot be empty  
- @Size → restrict length  

---

### Error Handling

- Used to return proper HTTP responses  

Examples:

- 400 → Bad Request (invalid input)  
- 404 → Not Found (resource missing)  
- 201 → Created (new resource created)  
- 200 → OK (successful request)  

---

## 7. Full Backend Flow (Updated)

    Client (REST Client / Browser)
            ↓
    Tomcat Server
            ↓
    Controller (receives request)
            ↓
    DTO (validates input)
            ↓
    Service (business logic)
            ↓
    Entity (converted from DTO)
            ↓
    Repository (JPA)
            ↓
    Hibernate (ORM)
            ↓
    PostgreSQL (Database)
            ↓
    Entity
            ↓
    Response DTO
            ↓
    Controller → Client

---

## Notes

- Spring Boot simplifies backend development with auto-configuration  
- JPA reduces the need to write SQL manually  
- Understanding SQL is important for debugging and performance  
- Backend flow should always be understood end-to-end  
- DTO helps maintain clean architecture and separation of concerns  