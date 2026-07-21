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

## RestTemplateBuilder

RestTemplateBuilder is a helper class provided by Spring Boot to create and configure `RestTemplate` instances.

Instead of creating a `RestTemplate` directly using `new`, Spring recommends using `RestTemplateBuilder` because it supports centralized configuration.

Example:

    RestTemplate restTemplate = restTemplateBuilder.build();

---

## ResponseEntity

`ResponseEntity` represents the complete HTTP response.

It contains:

- Response body
- HTTP status code
- HTTP headers

When consuming an API, `ResponseEntity` allows access to both the response data and metadata.

---

## getForObject()

`getForObject()` sends an HTTP GET request and returns only the response body.

Example:

    User user = restTemplate.getForObject(url, User.class);

Use it when only the response body is required.

---

## getForEntity()

`getForEntity()` sends an HTTP GET request and returns a `ResponseEntity`.

Example:

    ResponseEntity<User> response =
            restTemplate.getForEntity(url, User.class);

From the response you can access:

- Response body
- HTTP status
- HTTP headers

Use it when HTTP response details are important.

---

## Interview Point

RestTemplate is commonly used in legacy Spring applications.

For newer Spring Boot applications, WebClient is the recommended alternative because it supports reactive and non-blocking communication.