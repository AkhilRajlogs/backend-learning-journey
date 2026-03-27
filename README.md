# Backend Learning Journey  

This repository documents my transition from Mechanical Engineering to Backend Development using Java.

With 6+ years of experience in the engineering industry, I am now focused on building production-oriented backend systems using Java, Spring Boot, SQL, and REST APIs.

---

## What This Repository Demonstrates

This repository is a structured backend learning workspace focused on:

- Core Java fundamentals and internals  
- Backend architecture (layered design, DTO pattern, exception handling)  
- REST API development using Spring Boot  
- Database integration using PostgreSQL and JPA/Hibernate  
- Writing clean, maintainable backend code  

---

## Repository Structure  

backend-learning-journey/  
│  
├── core-java/  
│   ├── collections/  
│   ├── dsa/  
│   ├── exceptionhandling/  
│   ├── multithreading/  
│   └── oops/  
│  
├── notes/  
│  
├── projects/  
│   ├── TicTacToe-Java/  
│   └── Othello-Java/  
│  
├── spring-boot/  
│   ├── hello-api/  
│   └── task-manager-api/  
│  
├── sql-practice/  
│  
└── README.md  

---

## Backend Services  

### Hello API (Spring Boot)

Location: `spring-boot/hello-api`  

A minimal Spring Boot service built to understand REST API fundamentals and application structure.

Status: Completed (Basic Version)

Features:

- Spring Boot project setup using Maven  
- REST controller implementation  
- JSON response handling  
- Embedded server execution  

Endpoints:

GET `/hello`  
GET `/status`  

Example response:

{
  "status": "running"
}

---

### Task Manager API

Location: `spring-boot/task-manager-api`  

A structured backend service implementing full CRUD operations with database persistence.

Status: Version 2 Completed

---

### Architecture

Controller → Service → Repository → Database

---

### Key Features

- Full CRUD REST API  
- PostgreSQL integration using Spring Data JPA  
- DTO pattern:
  - Request DTO for input validation  
  - Response DTO for controlled output  
- Validation using Jakarta annotations  
- Global exception handling using `@RestControllerAdvice`  
- Logging using SLF4J  
- Proper HTTP status codes (201, 400, 404)  
- Exception-driven flow (no null checks)

---

### Endpoints

POST `/tasks`  
GET `/tasks`  
GET `/tasks/{id}`  
PUT `/tasks/{id}`  
DELETE `/tasks/{id}`  

---

### Example Response

{
  "id": 1,
  "title": "Learn Spring Boot",
  "completed": false
}

---

### Tech Stack

- Java 17  
- Spring Boot  
- Spring Web  
- Spring Data JPA  
- PostgreSQL  
- Maven  

---

### Recent Improvements

- Introduced DTO pattern (Request and Response separation)  
- Added validation using Jakarta annotations  
- Implemented global exception handling  
- Refactored ID handling to use Long consistently  
- Added structured logging (INFO for flow, ERROR for exceptions)  

---

### Next Step

API response standardization:

Target format:

{
  "status": "success",
  "message": "Task fetched successfully",
  "data": { ... }
}

Goal:

- Consistent API responses  
- Better frontend integration  
- Production-style API design  

---

## Core Java Coverage  

- OOP fundamentals  
- Java Collections Framework  
- HashMap internals and implementation  
- equals() and hashCode() contract  
- HashSet, LinkedHashSet, TreeSet  
- Comparable and Comparator  
- Queue, PriorityQueue, ArrayDeque  
- Exception handling (custom and best practices)  
- Multithreading fundamentals  
- Executor Framework  

---

## Console Projects  

### TicTacToe  

- Object-oriented design  
- Game loop and state handling  

### Othello (Paused)  

- Board modeling  
- Move validation logic  

---

## Learning Progress  

### Phase 1 — Core Java  
Completed  

### Phase 2 — Backend Development  
In Progress  

Completed:

- Spring Boot fundamentals  
- REST API development  
- PostgreSQL integration  
- DTO pattern  
- Validation  
- Exception handling  
- Logging  

---

### Phase 3 — Portfolio Projects (Planned)

- Task Manager (refined production version)  
- Expense Tracker API  
- Production-style backend architecture  

---

## Goal  

To become a job-ready backend developer capable of building real-world backend systems using:

- Java  
- Spring Boot  
- SQL  
- REST APIs  

---

## Purpose  

This repository serves as a long-term backend learning workspace and portfolio demonstrating progression from core Java foundations to backend system development.