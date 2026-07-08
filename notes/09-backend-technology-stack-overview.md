# Backend Technology Stack Overview
  
## Overview

This document provides a high-level overview of the core technologies commonly used in a Java Spring Boot backend application and how they fit together.

---

## 1. Java and Build System Basics

### JAR (Java Archive)

- A `.jar` file is a packaged Java application or library  
- Similar to a `.zip` file containing:  
  - `.class` files (compiled Java code)  
  - Metadata  

Example: Spring Boot dependencies are downloaded as JAR files

---

### Classpath

- Classpath defines where Java looks for classes and libraries  

Without Maven:
- You manually manage classpath  

With Maven:
- Dependencies are automatically added to classpath  

Flow:  
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

Flow:  
Client → Tomcat → DispatcherServlet → Controller  

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

Flow:  
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

Example from my project:

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

Backend applications validate incoming requests and return appropriate HTTP responses when errors occur.

These topics are covered in more detail in later Spring Boot notes.
---

## 7. Full Backend Flow (Updated)
  
Client
↓
Controller
↓
DTO
↓
Service
↓
Entity
↓
Repository
↓
Hibernate
↓
PostgreSQL
↓
Controller
↓
Client
  
---

## Related Notes

- 10 – Spring Boot Request Flow (Detailed request lifecycle)
- 12 – Backend Architecture Notes (Layered architecture)
- 17 – Service Layer Deep Dive (Business logic responsibilities)
- 19 – Spring Annotations (Spring Boot fundamentals)

These notes provide detailed explanations of the concepts summarized in this overview.