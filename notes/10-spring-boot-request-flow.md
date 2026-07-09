# Spring Boot Request Lifecycle (Deep Dive)

## Overview

This document explains how a single HTTP request is processed internally in a Spring Boot application.
This flow is specific to Spring Boot (Spring MVC architecture)

---

## Step-by-Step Flow

1. Client sends HTTP request
2. Tomcat receives the request
3. DispatcherServlet receives and routes request
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
  
- Uses PostgreSQL via Spring Data JPA  
- Repository layer handles database interaction  

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

---

## Enhanced Internal Flow 

Client  
→ Tomcat  
→ DispatcherServlet  
→ HandlerMapping  
→ HandlerAdapter  
→ @RequestBody  
→ HttpMessageConverter  
→ Jackson (JSON → DTO)  
→ @Valid (Validation)  
→ Controller  
→ Service  
→ Repository  
→ Database  

Response:

Database  
→ Entity  
→ Service  
→ Controller  
→ DispatcherServlet  
→ HttpMessageConverter → Jackson (DTO → JSON)
→ Client  

---

## Failure Scenarios

### 1. Invalid JSON

→ Jackson fails  
→ HttpMessageNotReadableException  
→ ExceptionHandler  
→ 400 Response  
→ DTO is NOT created

### 2. Validation Failure

→ DTO created  
→ Validation fails  
→ MethodArgumentNotValidException  
→ ExceptionHandler  
→ 400 Response  

---

## Request Data Binding Annotations

### @RequestParam

- Used for query parameters  
- Example: /tasks?completed=true  
- Binds simple values from URL  

---

### @PathVariable

- Used for URL path variables  
- Example: /tasks/{id}  
- Identifies specific resource  

---

### @RequestBody

- Used for JSON request body  
- Converts JSON → DTO using Jackson  
- Supports validation with @Valid  

---

## Related Notes

- 12 – Spring MVC Internals (DispatcherServlet, HandlerMapping, Jackson, validation internals)
- 20 – Spring MVC (Model, View, JSP, RequestParam, ModelAndView)