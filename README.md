# Backend Learning Journey  
  
This repository documents my structured transition from Mechanical Engineering to Backend Development using Java.
  
With 6+ years of experience in the engineering industry, I am now focused on building production-oriented backend development skills using Java, Spring Boot, SQL, and REST APIs.  
  
The goal of this repository is to demonstrate practical backend development knowledge through:  
  
- Core Java fundamentals  
- Backend system design concepts  
- Console-based Java projects  
- Spring Boot backend services  
- Database integration using SQL and JPA  
  
---  
   
## Repository Structure  

```  
backend-learning-journey/  
│
├── core-java/
│   ├── collections/
│   ├── dsa-basics/
│   ├── exceptionhandling/
│   ├── multithreading/
│   └── oops/payment/
│
├── notes/
│
├── projects/
│   ├── TicTacToe/
│   └── Othello/
│
├── spring-boot/
│
├── sql-practice/
│
└── README.md
```  
  
---  
  
## Backend Services  
  
### Hello API (Spring Boot)  
  
Location:  
  
spring-boot/hello-api  
  
A minimal Spring Boot service built to understand backend service structure and REST API development.  
  
Status: Completed (Basic Version)  
  
Features:  
  
- Spring Boot project setup using Maven  
- REST controller implementation  
- JSON response generation  
- Embedded server execution  
  
Endpoints:  
  
GET /hello    
Returns a plain text response.  
  
GET /status    
Returns a JSON response indicating service health.  
  
Example Response:  
  
{  
  "status": "running"  
}  
  
Run locally:  
  
mvn spring-boot:run  
  
Server runs on:  
  
http://localhost:8080  
  
---  
  
## Running a Core Java Example  
  
Requires **Java 17+** installed and added to PATH.  
  
From the project root:  
  
Compile:  
  
javac -d bin core-java/oops/payment/*.java  
  
Run:  
  
java -cp bin oops.payment.PaymentDemo  
  
---  
  
## Projects  
  
### TicTacToe  
  
A playable console-based TicTacToe implementation demonstrating:  
   
- Object-Oriented Design  
- Game loop logic  
- Board state management  
  
---  
  
### Othello (Work In Progress)  
  
Implementation of the classic Othello board game with focus on:  
  
- Game board modeling  
- Move validation logic  
- Turn-based gameplay structure  
  
Development was paused while focusing on backend engineering fundamentals.  
  
---  
  
## Tech Stack  
  
- Java  
- Object-Oriented Programming  
- Java Collections Framework  
- Multithreading & Concurrency  
- Git & GitHub  
- Spring Boot (Fundamentals completed, building APIs)  
- SQL  
- JPA / Hibernate  
- REST APIs  
  
---  
  
## Learning Progress  
  
### Phase 1 — Core Java Foundations  
  
Completed  
  
✔ OOP fundamentals    
✔ Java Collections Framework    
✔ ArrayList and LinkedList    
✔ HashMap and HashMap internals  
✔ equals() and hashCode() contract  
✔ HashSet and LinkedHashSet  
✔ TreeSet  
✔ Comparable and Comparator  
✔ Queue fundamentals  
✔ PriorityQueue  
✔ ArrayDeque  
✔ Exception Handling  
✔ Multithreading fundamentals  
✔ Executor Framework  
  
---  
  
### Phase 2 — Backend Development (In Progress)  
  
- Spring Boot fundamentals   
- REST API development  
- PostgreSQL integration  
- JPA / Hibernate  
- Backend architecture patterns
  
---  
  
### Phase 3 — Portfolio Projects (Planned)  
  
- Task Manager REST API   
- Expense Tracker Backend   
- Production-style backend architecture with Spring Boot  
  
---  
  
## Purpose of This Repository  
  
This repository serves as a long-term backend learning workspace and portfolio demonstrating the progression from core Java foundations to real backend system development.  
  
The repository will continue to evolve as more backend systems and projects are built.  