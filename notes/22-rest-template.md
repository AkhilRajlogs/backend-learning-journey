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

Use it when HTTP response details(metadata) are important.

---

## getForObject() vs getForEntity()

| getForObject() | getForEntity() |
|----------------|----------------|
| Returns only the response body | Returns the complete HTTP response |
| Cannot access status code or headers | Can access body, status code, and headers |
| Simpler for basic API calls | Useful when response metadata is needed |

---

## exchange()

The exchange() method is the most flexible way to send HTTP requests using RestTemplate.

It supports:

- Any HTTP method (GET, POST, PUT, DELETE, etc.)
- Custom request headers
- Request body
- ResponseEntity as the response

Example:

```java
HttpEntity<String> entity = new HttpEntity<>(headers);

ResponseEntity<User> response =
        restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                User.class
        );
```

---

## Message Converters

RestTemplate uses HttpMessageConverter implementations to convert data between Java objects and HTTP messages.

Common converters include:

- StringHttpMessageConverter → Handles plain text
- ByteArrayHttpMessageConverter → Handles byte arrays
- MappingJackson2HttpMessageConverter → Handles JSON using Jackson

These converters automatically serialize Java objects into HTTP requests and deserialize HTTP responses into Java objects.

---

## Error Handling

If an HTTP request returns an error status code, RestTemplate throws exceptions.

Common exceptions include:

- HttpClientErrorException → 4xx client errors
- HttpServerErrorException → 5xx server errors

These exceptions can be caught and handled to provide appropriate application behavior.

---

## Interview Point

- `RestTemplate` is used to consume REST APIs.
- `RestTemplateBuilder` is the preferred way to create a `RestTemplate`.
- `getForObject()` returns only the response body.
- `getForEntity()` returns the entire HTTP response as a `ResponseEntity`.

RestTemplate is commonly used in legacy Spring applications.

For newer Spring Boot applications, WebClient is the recommended alternative because it supports reactive and non-blocking communication.

RestTemplate simplifies communication with external REST APIs by handling request creation, response conversion, and error handling automatically.

Although still widely used in existing applications, WebClient is the recommended alternative for new Spring Boot projects.
