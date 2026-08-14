# Spring Annotations

## Overview

This note summarizes the most commonly used Spring and Spring Boot annotations, their purposes, and where they are typically used in a backend application.

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

Returns the method result directly as the HTTP response body, typically JSON.

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

## Lombok

Lombok is a Java library that reduces boilerplate code by generating commonly used methods and constructors through annotations.

### @Data

`@Data` generates commonly used methods such as:

* Getters
* Setters
* `toString()`
* `equals()`
* `hashCode()`
* Required-arguments constructor

It is commonly used in DTOs and model classes when these methods are needed.

### @AllArgsConstructor

Generates a constructor containing all fields of the class as parameters.

### @NoArgsConstructor

Generates a no-argument constructor.

### Why Lombok is Useful

Lombok reduces repetitive boilerplate code and makes classes such as DTOs shorter and easier to read.

For example, instead of manually writing getters, setters, and constructors, Lombok annotations can generate them automatically during compilation.

---

## Evolution of Spring Application Configuration
  
Spring applications can be started in different ways depending on the type of application.

### ClassPathXmlApplicationContext

- Uses XML configuration
- Beans are defined inside XML files

### AnnotationConfigApplicationContext

- Uses Java-based configuration instead of XML
- Registers beans using @Configuration classes and annotations

### SpringApplication.run()

Starts the Spring Boot application.

Responsibilities:

- Creates the ApplicationContext
- Performs component scanning
- Creates and manages beans
- Starts the embedded Tomcat server

---

## Related Notes

- 13 – Spring Boot Auto Configuration
- 20 – Spring MVC