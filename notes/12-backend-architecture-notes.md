# Backend Architecture Notes

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

## 4. Request Flow (Complete)

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

## 5. Layered Architecture

Controller → Service → Repository → Database

### Responsibilities:

- Controller
  - Handles HTTP layer
  - Uses DTOs

- Service
  - Contains business logic
  - Works with Entities

- Repository
  - Handles database operations using JPA

---

## 6. What SHOULD NOT be in Service Layer

- @RequestBody
- @PathVariable
- ResponseEntity
- HTTP status codes

Service must remain independent of web layer

---

## 7. Entity vs DTO

### Entity
- Represents database table

### DTO
- Defines API request/response structure
- Used for validation
- Hides internal fields

---

## 8. Why DTO is Used

- Prevents exposing database structure
- Decouples API from DB
- Enables validation
- Improves security
- Allows custom API contracts

---

## 9. Backend Flow (Detailed)

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

## 10. Exception Handling (Deep Dive)

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

## Validation Flow (Spring Boot + Jakarta Validation)
    
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

## JSON Flow (Jackson)

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

## JSON Parsing vs Validation

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

## @RequestBody Internals

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

## Important Validation Note

- Validation annotations (@NotBlank, @Size, etc.) do NOT run automatically
- @Valid (or @Validated) is REQUIRED to trigger validation

Without @Valid:
- Invalid data is accepted
- No exception is thrown
- Controller receives raw data

---

## Request Walkthrough (POST /tasks)

1. Request → Tomcat  
2. DispatcherServlet  
3. HandlerMapping  
4. HandlerAdapter  
5. @RequestBody → DTO  
6. @Valid → validation  
7. Controller  
8. Service  
9. Repository  
10. DB  
11. Entity returned  
12. Response DTO  
13. Jackson (Java → JSON)  
14. Response sent  

---

## Exception Walkthrough (Task Not Found)

1. Controller calls service  
2. Service throws exception  
3. DispatcherServlet catches  
4. HandlerExceptionResolver  
5. @RestControllerAdvice  
6. ResponseEntity (404)  
7. JSON response sent  

---

## Interview Quick Summary

- DispatcherServlet is the front controller
- HandlerMapping finds the controller
- HandlerAdapter executes the controller
- HttpMessageConverter (Jackson) handles JSON conversion
- @Valid triggers validation
- Service contains business logic
- Repository interacts with database
- @RestControllerAdvice handles exceptions globally

--- 

## Common Exceptions

- HttpMessageNotReadableException → Invalid JSON
- MethodArgumentNotValidException → Validation failure
- TaskNotFoundException → Custom business exception

---

## Response Flow (IMPORTANT)

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