# Spring Annotations

## @Component

Marks a class as a Spring Bean so that Spring can detect and manage it.

---

## @Service

Specialized @Component used for Service layer classes.

---

## @Repository

Specialized @Component used for Repository layer classes.

Benefits:

- Improves readability
- Enables exception translation

---

## @Controller

Used for handling web requests.

---

## @RestController

Combination of:

- @Controller
- @ResponseBody

---

## @Autowired

Used by Spring to inject dependencies automatically.

Spring searches the ApplicationContext for a matching Bean and injects it into the dependent class.
  
---

## Component Scanning

Spring scans specified packages for classes annotated with stereotype annotations such as:

- @Component
- @Service
- @Repository
- @Controller

Discovered classes are automatically registered as Spring Beans.

---

## @Qualifier

Used with @Autowired to specify which bean should be injected when multiple beans of the same type exist.

Without @Qualifier, Spring may throw NoUniqueBeanDefinitionException.

---

## @SpringBootApplication

Main annotation used to start a Spring Boot application.

Combines:

- @Configuration
- @EnableAutoConfiguration
- @ComponentScan

---

## @Configuration

Indicates that a class contains Spring Bean definitions.

Used to configure the application using Java-based configuration instead of XML.

---

## @Scope

Used to define bean scope.

Common scopes:

- Singleton
- Prototype
- Request
- Session

Default scope: Singleton

---

## SpringApplication.run()

Starts the Spring Boot application.

Responsibilities:

- Creates the ApplicationContext
- Performs component scanning
- Creates and manages beans
- Starts the embedded Tomcat server