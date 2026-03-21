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

## Full Backend Flow

Client (Postman / Browser)  
        ↓  
Tomcat Server  
        ↓  
@RestController  
        ↓  
Service Layer  
        ↓  
Repository (JPA)  
        ↓  
Hibernate (ORM)  
        ↓  
PostgreSQL (Database)  

---

## Notes

- Spring Boot simplifies backend development with auto-configuration  
- JPA reduces the need to write SQL manually  
- Understanding SQL is important for debugging and performance  
- Backend flow should always be understood end-to-end  