# Backend Learning Journey — Project Structure

This document represents the full local workspace structure for my backend learning journey.

---

## Root Structure

backend-learning-journey/

├── .vscode/
├── bin/
├── core-java/
│   ├── collections/
│   ├── dsa/
│   ├── exceptionhandling/
│   ├── multithreading/
│   └── oops/
│
├── notes/
│   ├── 00-project-structure.md
│   ├── 01-git-workflow.md
│   ├── ...
│   ├── 19-spring-annotations.md
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
├── README.md
└── TRACKER.md (local progress tracker, not tracked in Git)

---

## 1. Core Java

core-java/

### Collections

- ArrayDequeDemo.java
- ArrayListDemo.java
- ComparableDemo.java
- ComparatorDemo.java
- HashMapDemo.java
- HashSetDemo.java
- LinkedHashSetDemo.java
- LinkedListDemo.java
- LinkedListQueueDemo.java
- ListPolymorphismDemo.java
- PerformanceComparison.java
- PriorityQueueDemo.java
- QueueDemo.java
- SimpleHashMap.java
- TreeSetDemo.java
- README.md

### DSA

#### Arrays

- two-sum.md
- TwoSum.java

(Currently paused while focusing on backend development.)

### Exception Handling

- BasicExceptionDemo.java
- CustomExceptionDemo.java
- InvalidAgeException.java
- MultipleCatchDemo.java

### Multithreading

- ThreadDemo.java
- RunnableDemo.java
- ExecutorDemo.java
- SingleThreadExecutorDemo.java
- RaceConditionDemo.java

### OOPS (Payment System)

- BasePayment.java
- PaymentMethod.java
- CreditCardPayment.java
- NetBankingPayment.java
- UPIPayment.java
- WalletPayment.java
- Refundable.java
- PaymentDemo.java

---

## 2. Notes

notes/

- 00-project-structure.md

Core Java
- 01-git-workflow.md
- 02-oops-principles.md
- 03-collections.md
- 03.3-hashmap-internals.md
- 03.4-equals-hashcode-contract.md
- 03.5-hashset.md
- 03.6-linkedhashset.md
- 03.7-treeset.md
- 03.8-comparable.md
- 03.9-comparator.md
- 03.10-queue.md
- 03.11-priorityqueue.md
- 03.12-arraydeque.md
- 03.13-linkedlist-queue.md
- 03.14-choosing-the-right-collection.md

- 04-exceptions-in-java.md
- 05-multithreading-foundations.md
- 06-executorservice-basics.md

Spring Boot
- 07-spring-boot-hello-api.md
- 08-sql-basics.md
- 09-backend-technology-stack-overview.md
- 10-spring-boot-request-flow.md
- 11-task-manager-api.md
- 12-spring-mvc-internals.md
- 13-spring-boot-auto-configuration.md
- 14-spring-boot-interview-quick-notes.md
- 15-xml-vs-json.md
- 16-core-concepts.md
- 17-service-layer-deep-dive.md
- 18-interview-preparation-roadmap.md
- 19-spring-annotations.md
- 20-spring-mvc.md
- 21-rest-web-services.md

---

## 3. Projects

projects/

- TicTacToe-Java  
- Othello-Java (Incomplete)

---

## 4. Spring Boot

spring-boot/

### hello-api
(Basic Spring Boot starter project)

### task-manager-api

- .mvn/
- src/
- screenshots/
- target/
- pom.xml
- mvnw
- mvnw.cmd
- HELP.md
- test.http

Features:
- REST API (CRUD)
- DTO pattern
- Validation
- Global exception handling
- PostgreSQL integration

---

## 5. SQL Practice

Currently contains:

- 01-basic-queries.sql

Additional SQL practice will be added as learning progresses.

---

## Current Focus

- Spring Boot Backend Development
- REST APIs
- Interview Preparation
- Java Backend Transition

---

## Status Summary

Core Java → Completed

Spring Boot
- Spring MVC → Completed
- REST APIs → In Progress

Projects

- Hello API → Completed
- Task Manager API → Completed

Interview Preparation
- Notes → In Progress
- Revision → In Progress