# Spring MVC Internals  

## Overview

This note explains the internal Spring MVC components involved in processing an HTTP request after it reaches the DispatcherServlet.

It complements Note 10, which focuses on the overall request lifecycle.

---

## 1. MVC (Model-View-Controller)

- Model → Application data (Entity)
- View → UI (not used in REST APIs)
- Controller → Handles HTTP requests

In REST APIs:
- We return JSON instead of a View

---

## 2. DispatcherServlet (Core of Spring MVC)

- Front controller of Spring MVC
- Receives all incoming HTTP requests from Tomcat
- Routes request using HandlerMapping
- Executes controller using HandlerAdapter

### Responsibilities:

- Routing (URL → controller)
- Request parsing (JSON → Java object via HttpMessageConverter)
- Triggering validation
- Handling exceptions
- Response conversion (Java → JSON via Jackson)

---

## 3. Why DispatcherServlet Exists

- Centralizes request handling
- Avoids tight coupling
- Enables routing, validation, interceptors, exception handling

---

## 4. Request Flow (High-Level)

Client  
→ Tomcat  
→ DispatcherServlet  
→ HandlerMapping  
→ HandlerAdapter  
→ Controller  
→ Service  
→ Repository  
→ Database  
→ Entity  
→ Response DTO  
→ DispatcherServlet  
→ HttpMessageConverter (Jackson)  
→ JSON  
→ Client  

---

## 5. Request Processing Internals

Client  
→ DispatcherServlet  
→ HandlerMapping  
→ HandlerAdapter  
→ @RequestBody (Jackson → DTO)  
→ @Valid (Validation)  
→ Controller  
→ Service  
→ Entity  
→ Repository  
→ Database  
→ Entity  
→ Response DTO  
→ DispatcherServlet  
→ HttpMessageConverter  
→ Jackson (Java → JSON)  
→ Client  
  
---

## 6. Exception Handling (Deep Dive)

### Flow:

Exception occurs  
→ DispatcherServlet catches  
→ HandlerExceptionResolver  
→ @ExceptionHandler / @RestControllerAdvice  
→ Response returned  

---

### Key Components:

- DispatcherServlet
- HandlerExceptionResolver
- ExceptionHandlerExceptionResolver

---

### Why @RestControllerAdvice?

- Centralized handling
- Clean controllers
- Consistent API responses

---

### Key Notes:

- @ControllerAdvice → for views
- @RestControllerAdvice → for JSON APIs

- DefaultHandlerExceptionResolver:
  - Handles standard Spring exceptions (405, 415, etc.)

- Most specific exception handler is chosen

---

## 7. Validation Flow (Spring Boot + Jakarta Validation)
    
Client  
→ DispatcherServlet  
→ HandlerMapping  
→ HandlerAdapter  
→ @RequestBody (JSON → DTO)  
→ @Valid  
→ MethodArgumentNotValidException (if fails)  
→ ExceptionHandler  
→ Response  

---

## 8. JSON Flow (Jackson)

Client  
→ DispatcherServlet  
→ HandlerMapping  
→ HandlerAdapter  
→ HttpMessageConverter    
→ Jackson (JSON → DTO)  
→ Controller  
→ Jackson (DTO → JSON)  
→ Client  

---

## 9. JSON Parsing vs Validation

### Invalid JSON

→ Jackson fails  
→ HttpMessageNotReadableException  
→ ExceptionHandler  
→ 400 Response  

---

### Valid JSON but Validation Fails

→ DTO created  
→ Validation fails  
→ MethodArgumentNotValidException  
→ ExceptionHandler  
→ 400 Response  

---

### Important Notes

- Invalid JSON → DTO NOT created  
- Valid JSON → DTO created → validation runs  
- Wrong data type → Jackson error  
- Extra fields → ignored by default  
- Unknown JSON fields are ignored by default (can be configured using Jackson)

---

## 10. @RequestBody Internals

- @RequestBody is handled by RequestResponseBodyMethodProcessor
- It uses HttpMessageConverter to convert request body
- For JSON, MappingJackson2HttpMessageConverter is used
- Jackson ObjectMapper converts JSON → DTO

### Flow:

Client  
→ DispatcherServlet  
→ HandlerMapping  
→ HandlerAdapter  
→ RequestResponseBodyMethodProcessor  
→ HttpMessageConverter  
→ Jackson (JSON → DTO)  
→ @Valid (optional validation)  
→ Controller  

---

## 11. Why @Valid Matters

- Validation annotations (@NotBlank, @Size, etc.) do NOT run automatically
- @Valid (or @Validated) is REQUIRED to trigger validation

Without @Valid:
- Invalid data is accepted
- No exception is thrown
- Controller receives raw data

---

## 12. Interview Quick Summary

- DispatcherServlet is Spring MVC's front controller.
- HandlerMapping locates the controller method.
- HandlerAdapter invokes the controller method.
- HttpMessageConverter (Jackson) converts JSON and Java objects.
- @Valid triggers Jakarta Bean Validation.
- @RestControllerAdvice centralizes exception handling.
- Service contains business logic.
- Repository performs persistence operations.

--- 

## 13. Common Exceptions

- HttpMessageNotReadableException → Invalid JSON
- MethodArgumentNotValidException → Validation failure
- TaskNotFoundException → Custom business exception

---

## 14. Response Flow (IMPORTANT)

After controller returns response:

→ DispatcherServlet receives return value  
→ HandlerAdapter processes it  
→ ResponseBodyAdvice (optional)  
→ HttpMessageConverter selected  
→ Jackson converts Java object → JSON  
→ HTTP response sent to client  

---

### Key Notes:

- @ResponseBody or @RestController enables JSON response
- Same HttpMessageConverter is used for request and response
- Jackson ObjectMapper handles serialization