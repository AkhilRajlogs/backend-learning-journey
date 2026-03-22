# Spring Boot Request Lifecycle (Deep Dive)

## Overview

This document explains how a single HTTP request is processed internally in a Spring Boot application.

---

## Step-by-Step Flow

1. Client sends HTTP request
2. Tomcat receives the request
3. DispatcherServlet routes request
4. Controller handles request
5. Service processes logic
6. Data layer interacts with storage
7. Response returned to client

---

## Detailed Explanation

### 1. Client

- Sends HTTP request (GET, POST, etc.)

---

### 2. Tomcat Server

- Embedded server inside Spring Boot
- Listens on port 8080
- Receives incoming requests

---

### 3. DispatcherServlet (IMPORTANT)

- Front controller of Spring
- Decides which controller should handle request

💡 This is the "brain" of request routing

---

### 4. Controller

- Handles HTTP request
- Maps URL to method

---

### 5. Service Layer

- Contains business logic
- Processes request data

---

### 6. Data Layer

Current:
- ArrayList (temporary)

Future:
- PostgreSQL via JPA

---

### 7. Response

- Converted to JSON
- Sent back to client

---

## Key Learning

- DispatcherServlet is central to Spring request handling
- Controller should remain thin
- Service handles logic
- Data layer handles persistence