# Spring Boot Interview Quick Notes

## Overview

This note summarizes common Spring Boot interview questions and concise answers for quick revision. Detailed explanations are available in the related notes.
  
## Request Lifecycle in Spring Boot

Typical request flow:

Client
→ Tomcat
→ DispatcherServlet
→ HandlerMapping
→ HandlerAdapter
→ Controller
→ Service
→ Repository
→ Database
→ Response
→ Client

### Interview Points

- DispatcherServlet is the front controller.
- HandlerMapping locates the controller method.
- HandlerAdapter invokes the controller.
- Jackson and HttpMessageConverter handle JSON conversion.
- @Valid triggers request validation.

---

## Validation vs JSON Parsing Failure

### Invalid JSON
- Jackson fails during parsing
- DTO is not created
- HttpMessageNotReadableException occurs

### Validation Failure
- DTO is created successfully
- @Valid triggers validation
- MethodArgumentNotValidException occurs

### Interview Tip

JSON parsing happens before validation. If JSON cannot be parsed, validation never runs.

---

## Pagination in Spring Boot

Pagination is implemented using:

- Page
- Pageable
- PageRequest

### Interview Points
  
- Avoids loading huge datasets
- Improves scalability
- Reduces memory usage
- Common production requirement

---

## Related Notes

- 10 – Spring Boot Request Lifecycle
- 11 – Task Manager API
- 12 – Spring MVC Internals