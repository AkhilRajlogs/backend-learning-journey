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
- **Development Speed:** Initial development can be faster because there is no need to manage communication between separate services.

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

---

## Service Discovery and Registration

**Eureka** is a service discovery and registration tool developed by Netflix. It allows microservices to register themselves with a central registry and discover other services without hardcoding their network locations.

### How Eureka Works

1. **Eureka Server:** Acts as a central registry, maintaining information about registered services, their instances, and health status.
2. **Service Registration:** When a microservice starts, it registers details such as its service name, instance ID, host, and port with the Eureka Server.
3. **Heartbeats and Health Checks:** Registered services periodically send heartbeats to indicate that they are running. Eureka uses this information to maintain service availability details.
4. **Service Discovery:** A microservice queries Eureka to find the network location of another registered service.
5. **Load Balancing:** Eureka can work with load balancers such as Netflix Ribbon to distribute requests across multiple service instances.
6. **High Availability:** Multiple Eureka Server instances can be deployed to improve availability and fault tolerance.

### Example: CN Kart Application

**1. Discovery Server**

Enable Eureka Server in the Spring Boot application:

```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            DiscoveryServerApplication.class, args
        );
    }
}
```

**Discovery Server Configuration (`application.yml`):**

```yaml
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/

server:
  port: 8761
```

The Discovery Server maintains the service registry and does not register itself as a client in this configuration.

**2. Discovery Client**

Enable Eureka Client in a microservice such as Inventory Service:

```java
@SpringBootApplication
@EnableEurekaClient
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }
}
```

The Inventory Service registers itself with the Eureka Server so that other services can discover it.

Other service discovery tools include **Consul** and **ZooKeeper**, which provide similar functionalities.

### Quick Recall

- **Eureka Server** — maintains the service registry.
- **Eureka Client** — registers with the server and discovers other services.
- **Service Registration** — adds a service instance to the registry.
- **Heartbeats** — indicate that a registered service is still running.
- **Service Discovery** — obtains the network location of another service.
- **Load Balancing** — distributes requests across service instances.

---

## Circuit Breaker – Hystrix

**Hystrix** is a Netflix library that helps build resilient microservices by handling failures and latency in distributed systems. It prevents failures in one service from cascading to other services.

### Circuit Breaker Pattern

The circuit breaker monitors calls to an external service and changes its state based on failures:

1. **Closed:** Requests are allowed to pass through to the service. Failures are monitored.
2. **Open:** When failures cross a configured threshold, requests are blocked temporarily, and a fallback method is executed.
3. **Half-Open:** After a configured waiting period, a limited number of requests are allowed to test whether the service has recovered.

### Key Features

- **Fallback Mechanism:** Provides an alternative response when a service call fails.
- **Metrics and Monitoring:** Tracks failures, request volume, and response times.
- **Dynamic Thresholds:** Hystrix adjusts thresholds based on real-time metrics to adapt to changing service conditions.
- **Request Caching:** Avoids repeated execution of identical requests.
- **Request Collapsing:** Combines multiple requests into a single request to reduce load.
- **Concurrency Control:** Limits concurrent requests to protect services from overload.

### Example: CN Kart Application

Hystrix can wrap external service calls in the Order Service to provide circuit-breaking and fallback behaviour.

```java
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @HystrixCommand(fallbackMethod = "fallbackPlaceOrder")
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Placing Order");
        return orderService.placeOrder(orderRequest);
    }

    public String fallbackPlaceOrder(@RequestBody OrderRequest orderRequest) {
        return "Service is Not available";
    }
}
```

If the service call fails or the circuit breaker is open, Hystrix executes the fallback method instead of allowing the failure to propagate.

### Important Configuration Parameters

- **Error Threshold Percentage:** Failure percentage at which the circuit breaker opens.
- **Request Volume Threshold:** Minimum number of requests required before evaluating the failure percentage.
- **Sleep Window:** Time the circuit breaker remains open before allowing test requests.

### Benefits

- Prevents cascading failures across microservices.
- Improves application resilience and user experience.
- Provides fallback responses during service failures.
- Helps monitor and manage service reliability.
- **Operational Insights:** Hystrix metrics help monitor service health and performance, supporting troubleshooting.

**Note:** The lecture mentions that Hystrix has been deprecated and points to Resilience4j and Spring Cloud alternatives.

### Quick Recall

- **Closed:** Requests pass through.
- **Open:** Requests are blocked; fallback is executed.
- **Half-Open:** Test requests check whether the service has recovered.
- **Fallback:** Alternative response during failure.
- **Thresholds:** Determine when the circuit breaker opens.
- **Purpose:** Prevent cascading failures.

---

## Feign Client

**Feign Client** is a declarative HTTP client that simplifies communication between microservices. It allows one service to call another service's REST APIs by defining an interface with appropriate annotations.

### Key Concepts

- **Declarative Communication:** Define HTTP calls through an interface rather than writing request-handling code manually.
- **Feign Client Interface:** Uses annotations to specify the target service and REST endpoint.
- **Service Discovery Integration:** Can work with Eureka to locate service instances.
- **Load Balancing:** Can distribute requests across available service instances when integrated with a load-balancing mechanism.

### Example: CN Kart Application

The Inventory Service exposes an endpoint to check product availability. The Order Service uses a Feign Client interface to call this endpoint.

**1. Inventory Controller**

```java
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(
        @RequestParam Long skuCode,
        @RequestParam Integer qty
    ) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return inventoryService.isInStock(skuCode, qty);
    }
}
```

**2. Feign Client in Order Service**

```java
@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryService {

    @GetMapping("/api/inventory?skuCode={skuCode}&qty={qty}")
    public boolean isInStock(
        @RequestParam Long skuCode,
        @RequestParam Integer qty
    );
}
```

The Feign Client uses the service name `INVENTORY-SERVICE` to communicate with the Inventory Service. It abstracts the HTTP communication and integrates with Spring Cloud components such as Eureka and Ribbon.

### Quick Recall

- **Feign Client:** Declarative HTTP client for inter-service communication.
- **`@FeignClient`:** Defines the target service.
- **`@GetMapping`:** Specifies the REST endpoint.
- **Eureka Integration:** Supports service discovery.
- **Ribbon Integration:** Supports load balancing.
- **Advantage:** Reduces boilerplate code and simplifies API communication.

---

## API Gateway

An **API Gateway** acts as a single entry point for client requests in a microservices architecture. It receives incoming requests and routes them to the appropriate microservices.

Instead of clients communicating directly with multiple services, they interact through the API Gateway.

### Key Responsibilities

- **Request Routing:** Directs incoming requests to the appropriate microservice.
- **Authentication and Security:** Centralises authentication, authorisation, and encryption to protect services from unauthorised access.
- **Load Balancing:** Distributes requests across available service instances.
- **Request Aggregation:** Combines responses from multiple services into a single response.
- **Caching:** Stores frequently requested data to improve response times.
- **Rate Limiting and Quotas:** Controls incoming request rates and quotas to prevent abuse and ensure fair usage.
- **Monitoring and Analytics:** Tracks API usage and performance.
- **Versioning:** Supports different versions of APIs.
- **Response Transformation:** Modifies responses before returning them to clients.
- **Logging and Tracing:** Helps track requests across microservices.
- **Simplified Client Code:** Hides the complexities of communication with multiple services, allowing clients to interact through a single gateway.
- **Microservices Independence:** Shields clients from internal service changes, allowing services to be added, removed, or replaced without affecting clients.

### Benefits

- Provides a unified entry point for clients.
- Reduces direct exposure of internal microservices.
- Centralizes common concerns such as security and routing.
- Simplifies client-side communication.

### Quick Recall

- **API Gateway:** Entry point for client requests.
- **Routing:** Forwards requests to the appropriate service.
- **Aggregation:** Combines responses from multiple services.
- **Rate Limiting:** Controls incoming request volume.
- **Security:** Centralizes authentication and security policies.

---

## Conclusion

Microservices divide complex applications into smaller, independently deployable services, each focused on a specific business capability and communicating through well-defined APIs or messaging systems.

### Key Benefits

- **Modular Scalability and Flexibility:** Services can be developed, deployed, updated, and scaled independently, with flexibility in technology choices.
- **Resilience and Specialisation:** Service boundaries help isolate failures and allow each service to focus on a specific business capability.
- **Decentralised Collaboration and Autonomy:** Teams can own and evolve services independently, supporting innovation and faster development.

Microservices also introduce challenges such as inter-service communication complexity and operational overhead. The decision to adopt this architecture should consider project scope, team expertise, scalability needs, and the associated trade-offs.