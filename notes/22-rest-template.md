# REST Template

## What is RestTemplate?

RestTemplate is a synchronous HTTP client provided by Spring.

It is used to consume REST APIs from another application.

Instead of receiving requests like a controller, RestTemplate sends HTTP requests to external services.

---

## Common Operations

- GET
- POST
- PUT
- DELETE

---

## Typical Flow

Application A
        │
        │ RestTemplate
        ▼
Application B (REST API)

---

## Interview Point

RestTemplate is commonly used in legacy Spring applications.

For newer Spring Boot applications, WebClient is the recommended alternative because it supports reactive and non-blocking communication.