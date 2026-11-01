# Microservices

## Introduction

Microservices is an architectural approach in which a complex application is divided into smaller, independently deployable services. Each service focuses on a specific business capability and communicates with other services through well-defined APIs or messaging systems.

This approach allows teams to develop and maintain services independently, supporting flexibility, scalability, and the use of different technologies.

---

## Monolithic vs Microservices Architecture

### Monolithic Architecture

A monolithic application is built as a single, unified codebase in which all components and functionalities are integrated into one deployable unit.

**Key Characteristics:**

- **Simplicity:** Easier to develop, test, and deploy initially.
- **Single Deployment:** The entire application is deployed as one unit.
- **Communication:** Components communicate within the same process, reducing overhead.
- **Debugging:** Troubleshooting can be easier because components are closely connected.
- **Scaling Challenges:** The entire application must be scaled, even when only one component needs additional resources.
- **Technology Stack:** All components generally use the same technology stack.

### Microservices Architecture

A microservices application consists of loosely coupled, independently deployable services, each focusing on a specific business capability.

**Key Characteristics:**

- **Modularity:** Services can be developed, deployed, and maintained independently.
- **Independent Scalability:** Individual services can be scaled according to their resource requirements.
- **Technology Diversity:** Different services can use different technologies and programming languages.
- **Fault Isolation:** Failure in one service does not necessarily bring down the entire application.
- **Development Flexibility:** Teams can work on different services concurrently.
- **Complexity:** Inter-service communication must be managed effectively.
- **Deployment and Operational Overhead:** Managing multiple services requires additional deployment, orchestration, and monitoring practices.

### Comparison

| Aspect | Monolithic | Microservices |
|---|---|---|
| Structure | Single integrated application | Collection of independent services |
| Deployment | Single deployable unit | Services deployed independently |
| Scalability | Entire application is scaled | Individual services can be scaled |
| Technology | Generally one technology stack | Different technology stacks can be used |
| Communication | Within the same process | Through APIs or messaging systems |
| Complexity | Simpler initially | More operational and communication complexity |

### Choosing Between Them

The choice depends on project scope, scalability requirements, team expertise, development speed, and operational complexity.

- **Monolithic:** Suitable for smaller projects with limited scalability requirements.
- **Microservices:** May be beneficial for larger, complex applications requiring independent scalability, technology diversity, and development flexibility.

Some projects begin with a monolithic architecture and gradually move selected components into microservices as requirements grow. Neither architecture is universally suitable; the choice depends on the project's needs.

---

## Principles of Microservices Design

1. **Single Responsibility Principle (SRP):** Each microservice should have one well-defined responsibility or business capability.

2. **Decentralised Data Management:** Each microservice should manage its own database or data store instead of tightly coupling services through a shared database.

3. **API-First Design:** Define clear and consistent APIs that act as contracts for communication between services.

4. **Loose Coupling:** Services should interact through well-defined APIs or messaging systems and remain independently changeable.

5. **Autonomous Development and Deployment:** Teams should be able to develop, test, deploy, and upgrade services independently.

6. **Technology Diversity:** Each service can use the technology stack best suited to its specific requirements.

7. **Resilience and Fault Isolation:** A failure in one service should not cause a system-wide failure. Mechanisms such as circuit breakers and retries help isolate failures.

8. **Event-Driven Architecture:** Services can use events and asynchronous communication to respond to changes within the system.

9. **Scalability and Load Distribution:** Scale individual services according to their workloads for efficient resource utilisation.

10. **Monitoring and Observability:** Use logging, tracking, and tracing to understand service health, performance, and potential issues.

11. **Continuous Integration and Continuous Deployment (CI/CD):** Automate testing, deployment, and updates to support consistent and reliable releases.

12. **Autonomy and Decentralisation:** Service teams should have control over their services' development, deployment, and maintenance.

13. **Polyglot Persistence:** Different microservices can use different data storage technologies based on their individual requirements.

---

## Inter-Service Communication

Microservices communicate with each other through APIs or messaging systems. Spring's `RestTemplate` provides a way to communicate with RESTful services by making HTTP requests.

### RestTemplate

`RestTemplate` is a Spring Framework class used to make HTTP requests to other services.

**Key Concepts:**

1. **Dependency and Configuration:** Include the required Spring dependencies and configure a `RestTemplate` bean. It can be customised with options such as timeouts, message converters, and authentication settings.

2. **Making HTTP Requests:** Supports HTTP methods such as GET, POST, PUT, and DELETE. Requests can include URLs, parameters, headers, and request bodies.

3. **Handling Responses:** Returns HTTP responses that can be converted into Java objects using configured message converters.

4. **Error Handling:** Handles HTTP errors such as 4xx and 5xx responses by default. Error handling can also be customised.

### Example: CN Kart Application

Order Service calls Inventory Service to check whether a product is available before placing an order.

```java
public String placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());
    order.setPrice(orderRequest.getPrice());
    order.setQuantity(orderRequest.getQuantity());
    order.setSkuCode(orderRequest.getSkuCode());

    // Check product availability through Inventory Service
    String resourceUrl =
        "http://localhost:8083/api/inventory?skuCode={skuCode}&qty={qty}";

    boolean isInStock = Boolean.TRUE.equals(
        restTemplate.getForEntity(
            resourceUrl,
            Boolean.class,
            orderRequest.getSkuCode(),
            orderRequest.getQuantity()
        ).getBody()
    );

    if (isInStock) {
        orderRepository.save(order);
        return "Order Placed";
    } else {
        return "Item is not in stock, please try again later";
    }
}
```

**Communication Flow:**

1. Order Service receives an order request.
2. It calls Inventory Service using `RestTemplate`.
3. Inventory Service checks the requested product quantity.
4. If the product is available, Order Service saves the order.
5. Otherwise, it returns an out-of-stock message.

### Quick Recall

- `RestTemplate` — makes HTTP requests to RESTful services.
- `getForEntity()` — sends a GET request and returns a `ResponseEntity`.
- `Boolean.class` — specifies the expected response body type in this example.
- `getBody()` — retrieves the response body.