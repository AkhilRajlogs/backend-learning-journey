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

Spring looks for a matching Bean and injects it into the dependent class.

---

## Component Scanning

Spring scans specified packages for classes annotated with stereotype annotations such as:

- @Component
- @Service
- @Repository
- @Controller

Discovered classes are automatically registered as Spring Beans.