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
- Routes request to correct controller using HandlerMapping
- Delegates execution using HandlerAdapter

### Responsibilities:

- Routing (URL → controller)
- Request parsing (JSON → Java object)
- Triggering validation
- Handling exceptions
- Response conversion (Java → JSON via Jackson)

---

## 3. Why DispatcherServlet Exists

- Centralizes request handling
- Avoids tight coupling
- Enables routing, validation, interceptors, exception handling

Without it:
- Manual routing required
- Code becomes unmaintainable

---

## 4. Request Flow (Complete)

Client  
→ Tomcat  
→ DispatcherServlet  
→ HandlerMapping  
→ Controller  
→ Service  
→ Repository  
→ Database  
→ Entity  
→ Response DTO  
→ DispatcherServlet  
→ JSON (via Jackson)  
→ Client  

---

## 5. Layered Architecture

Controller → Service → Repository → Database

### Responsibilities:

- Controller
  - Handles HTTP layer
  - Maps requests/responses

- Service
  - Contains business logic
  - Makes application decisions
  - Coordinates between layers

- Repository
  - Handles database operations using JPA

---

## 6. What SHOULD NOT be in Service Layer

- HTTP-specific logic:
  - @RequestBody
  - @PathVariable
  - ResponseEntity
  - HTTP status codes
  - API response formatting

Service should remain independent of web layer

---

## 7. Entity vs DTO

### Entity (Task.java)
- Represents database table
- Holds data structure

### DTO
- Controls API request/response
- Used for validation
- Prevents exposing internal structure

---

## 8. Why DTO is Used

- Prevents exposing database structure
- Decouples API from database
- Improves security
- Enables validation at API boundary

DTO protects entity and gives control over API

---

## 9. Backend Flow (Detailed)

Client  
→ Controller  
→ DTO (validation)  
→ Service  
→ Entity  
→ Repository  
→ Database  
→ Entity  
→ Response DTO  
→ Controller  
→ Client  

---

## 10. Exception Handling (Deep Dive)

### Flow:

Service throws exception  
→ DispatcherServlet catches it  
→ HandlerExceptionResolver processes it  
→ @ExceptionHandler executes  
→ Response returned  

---

### Key Components:

- DispatcherServlet → receives exception
- HandlerExceptionResolver → resolves exception
- ExceptionHandlerExceptionResolver → processes @ExceptionHandler

---

### Why NOT handle in Service?

- Violates separation of concerns
- Leads to repetitive try-catch blocks
- Mixes business logic with error handling

---

### Why @RestControllerAdvice?

- Centralized exception handling
- Clean controller and service
- Consistent API responses

---

### Key Notes:

- @ControllerAdvice → for MVC (views)
- @RestControllerAdvice → for REST APIs (JSON)

- If no handler found:
  → DefaultHandlerExceptionResolver → returns 500

- Multiple handlers:
  → Most specific exception is chosen

---

## 11. Key Design Summary

- Controller → handles HTTP
- Service → contains business logic
- Repository → interacts with database
- Entity → represents data
- DTO → shapes API data
- DispatcherServlet → manages entire flow

---

## Validation Flow

- Validation is defined in DTO using annotations
- Triggered using @Valid in controller
- If validation fails → MethodArgumentNotValidException
- Handled using @RestControllerAdvice

Flow:

Client → Controller → @Valid → Validation  
→ Exception → ExceptionHandler → Response

---

## JSON Flow (Jackson)

- @RequestBody converts JSON → Java object
- Uses Jackson internally
- Field names must match JSON keys
- After conversion → validation runs

Flow:

Client  
→ DispatcherServlet  
→ @RequestBody (Jackson: JSON → DTO)  
→ @Valid (Validation)  
→ Service  
→ Controller  
→ Jackson (Java → JSON)  
→ Client  

---

## Request Walkthrough (POST /tasks)

1. Request reaches Tomcat
2. DispatcherServlet receives it
3. Finds matching controller
4. @RequestBody → JSON to DTO
5. @Valid → validation runs
6. Controller calls service
7. Service converts DTO → Entity
8. Repository saves to DB
9. DB returns saved entity
10. Jackson converts to JSON
11. Response sent to client

## How Routing Works

- HTTP request has:
  - Method (GET, POST, etc.)
  - URL (/tasks)

- @PostMapping("/tasks") registers:
  (POST, "/tasks") → method

- DispatcherServlet:
  - Receives request
  - Uses HandlerMapping to find matching method
  - Calls that method

Example:

POST /tasks → createTask()
GET /tasks/5 → getTask(5)