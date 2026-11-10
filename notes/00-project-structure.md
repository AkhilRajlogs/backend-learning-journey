# Backend Learning Journey — Project Structure

This document represents the local workspace structure of my Java backend learning journey.

The repository contains Core Java demonstrations, DSA practice, Java projects, Spring Boot applications, SQL practice, and structured learning notes.

The structure is maintained incrementally as new topics and projects are added.

---

## Root Structure

```text
backend-learning-journey/
│
├── .vscode/
├── bin/
│
├── core-java/
│   ├── collections/
│   ├── dsa/
│   ├── exceptionhandling/
│   ├── multithreading/
│   └── oops/
│
├── notes/
│   ├── 00-project-structure.md
│   └── ... learning notes ...
│
├── projects/
│   ├── Othello-Java/
│   └── TicTacToe-Java/
│
├── spring-boot/
│   ├── hello-api/
│   └── task-manager-api/
│
├── sql-practice/
│   └── 01-basic-queries.sql
│
├── .gitignore
├── README.md
└── TRACKER.md
```

`TRACKER.md` is a local progress tracker and is not tracked in Git.

---

## 1. Core Java

`core-java/`

Contains Java demonstrations and implementations covering Collections, DSA, Exception Handling, Multithreading, and OOPS.

### 1.1 Collections

`core-java/collections/`

Demonstrations of Java Collections Framework components.

```text
collections/
├── ArrayDequeDemo.java
├── ArrayListDemo.java
├── ComparableDemo.java
├── ComparatorDemo.java
├── HashMapDemo.java
├── HashSetDemo.java
├── LinkedHashSetDemo.java
├── LinkedListDemo.java
├── LinkedListQueueDemo.java
├── ListPolymorphismDemo.java
├── PerformanceComparison.java
├── PriorityQueueDemo.java
├── QueueDemo.java
├── README.md
├── SimpleHashMap.java
└── TreeSetDemo.java
```

Topics covered:

- Lists, Sets, and Maps
- Queue and Deque implementations
- Sorting using Comparable and Comparator
- HashMap and HashSet behavior
- Collection performance comparisons

### 1.2 DSA

`core-java/dsa/`

Currently contains array-related problem-solving practice.

```text
dsa/
└── arrays/
    ├── two-sum.md
    └── TwoSum.java
```

The Two Sum problem includes a Markdown explanation and Java implementation.

Additional DSA topics and implementations will be added as the course progresses.

### 1.3 Exception Handling

`core-java/exceptionhandling/`

```text
exceptionhandling/
├── BasicExceptionDemo.java
├── CustomExceptionDemo.java
├── InvalidAgeException.java
└── MultipleCatchDemo.java
```

Covers basic exceptions, custom exceptions, and multiple catch blocks.

### 1.4 Multithreading

`core-java/multithreading/`

```text
multithreading/
├── ExecutorDemo.java
├── RaceConditionDemo.java
├── RunnableDemo.java
├── SingleThreadExecutorDemo.java
└── ThreadDemo.java
```

Covers Java threads, Runnable, ExecutorService, and race conditions.

### 1.5 OOPS — Payment System

`core-java/oops/payment/`

```text
oops/
└── payment/
    ├── BasePayment.java
    ├── CreditCardPayment.java
    ├── NetBankingPayment.java
    ├── PaymentDemo.java
    ├── PaymentMethod.java
    ├── Refundable.java
    ├── UPIPayment.java
    └── WalletPayment.java
```

A practical payment-system example demonstrating object-oriented programming concepts, inheritance, abstraction, interfaces, and polymorphism.

---

## 2. Notes

`notes/`

Contains structured Markdown notes for Java fundamentals, backend development, Spring Boot, Spring Security, and interview preparation.

### Core Java and Collections

- `01-git-workflow.md`
- `02-oops-principles.md`
- `03-collections.md`
- `03.3-hashmap-internals.md`
- `03.4-equals-hashcode-contract.md`
- `03.5-hashset.md`
- `03.6-linkedhashset.md`
- `03.7-treeset.md`
- `03.8-comparable.md`
- `03.9-comparator.md`
- `03.10-queue.md`
- `03.11-priorityqueue.md`
- `03.12-arraydeque.md`
- `03.13-linkedlist-queue.md`
- `03.14-choosing-the-right-collection.md`
- `04-exceptions-in-java.md`
- `05-multithreading-foundations.md`
- `06-executorservice-basics.md`

### Spring Boot and Backend Development

- `07-spring-boot-hello-api.md`
- `08-sql-basics.md`
- `09-backend-technology-stack-overview.md`
- `10-spring-boot-request-flow.md`
- `11-task-manager-api.md`
- `12-spring-mvc-internals.md`
- `13-spring-boot-auto-configuration.md`
- `14-spring-boot-interview-quick-notes.md`
- `15-xml-vs-json.md`
- `16-core-concepts.md`
- `17-service-layer-deep-dive.md`
- `18-interview-preparation-roadmap.md`
- `19-spring-annotations.md`
- `20-spring-mvc.md`
- `21-rest-web-services.md`
- `22-rest-template.md`
- `23-spring-data-jpa.md`
- `24-spring-security.md`
- `25-spring-security-user-persistence-jwt.md`
- `26-spring-security-password-encoding.md`
- `27-spring-security-oauth-oidc-keycloak.md`
- `28-spring-boot-actuator-dev-tools.md`
- `29-spring-boot-logging-metrics.md`
- `30-junit-testing.md`
- `31-microservices.md`

---

## 3. Standalone Java Projects

`projects/`

### 3.1 TicTacToe-Java

```text
TicTacToe-Java/
├── .vscode/
├── bin/
├── lib/
├── src/
└── README.md
```

A standalone Java Tic-Tac-Toe project.

### 3.2 Othello-Java

```text
Othello-Java/
├── bin/
├── src/
├── IMPROVEMENTS.md
└── README.md
```

An ongoing Java Othello project with documented improvement areas.

---

## 4. Spring Boot Projects

`spring-boot/`

Contains practical Spring Boot applications developed while learning backend development.

### 4.1 Hello API

`spring-boot/hello-api/`

```text
hello-api/
├── .mvn/
├── src/
├── target/
├── wrapper/
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```

A basic Spring Boot application for learning application setup and REST API fundamentals.

### 4.2 Task Manager API

`spring-boot/task-manager-api/`

```text
task-manager-api/
├── .mvn/
├── screenshots/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/akhilraj/task_manager_api/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── exception/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── Application.java
│   │   └── resources/
│   └── test/
├── target/
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── test.http
```

Features:

- REST API with CRUD operations
- DTO pattern
- Input validation
- Global exception handling
- PostgreSQL integration
- Layered application architecture

---

## 5. SQL Practice

`sql-practice/`

Currently contains:

```text
sql-practice/
└── 01-basic-queries.sql
```

Used for practicing SQL queries and database fundamentals.

Additional SQL exercises will be added as learning progresses.

---

## 6. Progress and Future Additions

The repository is continuously evolving alongside the Java backend learning journey.

Current areas of focus:

- Core Java and Collections
- Data Structures and Algorithms
- Spring Boot and REST APIs
- Spring Security and JWT
- Backend application development
- Database concepts and SQL
- Interview preparation

Future additions may include:

- Advanced DSA implementations
- Additional LeetCode solutions
- Bank Security Application
- Further backend projects
- Additional interview preparation material

The project structure will be updated as meaningful additions are made.