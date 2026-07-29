# Core Backend Concepts

## Overview

This note serves as a quick revision guide for commonly asked Spring Boot and backend interview concepts.

Detailed explanations are available in the related notes.

---

## Persistence (JPA / Hibernate)
  
---

## What is JPA?

JPA (Java Persistence API) is a specification that defines how Java objects are mapped to database tables.

---

## What is Hibernate?

Hibernate is the implementation of JPA. It handles ORM and generates SQL queries automatically.

---

## EntityManager

EntityManager is the primary JPA interface used to interact with the persistence context.

It manages the lifecycle of entities and provides operations such as:

- persist()
- merge()
- remove()
- find()

In Spring Boot applications using Spring Data JPA, developers usually interact with `JpaRepository`, while EntityManager works behind the scenes.

---

## Hibernate Session

Session is Hibernate's native interface for interacting with the database.

It provides Hibernate-specific features beyond the standard JPA API.  

Since Hibernate implements JPA, an EntityManager can be converted into a Hibernate Session when Hibernate-specific functionality is required.

Example:

```java
Session session = entityManager.unwrap(Session.class);
```

The `unwrap()` method returns the underlying Hibernate Session managed by the EntityManager.

---

## @Transactional

`@Transactional` tells Spring that a method should execute within a database transaction.

If all operations succeed, the transaction is committed.

If an exception occurs, Spring rolls back the transaction to maintain data consistency.

It is commonly applied at the Service layer, where multiple database operations should succeed or fail as a single unit.

---

## JPA Repository vs EntityManager vs Session

| Component | Purpose |
|-----------|---------|
| JpaRepository | High-level CRUD abstraction used in most Spring Boot applications |
| EntityManager | JPA interface for managing entity persistence |
| Session | Hibernate-specific implementation providing additional ORM features |  

Spring Data JPA repositories internally use EntityManager, so developers rarely need to interact with it directly.

### Interview Tip

In most Spring Boot applications, developers work with `JpaRepository`.

`EntityManager` and Hibernate `Session` are typically used only when lower-level persistence control or Hibernate-specific functionality is required.

---

## What is @Entity?

@Entity marks a class as a JPA entity and maps it to a database table.

It tells Hibernate to treat this class as a table and its fields as columns.

---

## What is @Id?

@Id marks the primary key of an entity and is used to uniquely identify each record in the database.

---

## What does persist mean?

persist() is an EntityManager method that makes a transient entity persistent by associating it with the current persistence context. Hibernate later synchronizes it with the database during the transaction.

---

## What does save() do?

The save() method is provided by JpaRepository. It uses JPA and Hibernate to save the entity in the database.  
save() typically executes within a transaction managed by Spring Data JPA.  

- If the entity is new → INSERT operation  
- If the entity already exists → UPDATE operation  

Internally, Hibernate decides whether to perform insert or update based on the entity state.

---

## save() vs saveAndFlush()

- save() → may delay DB write until transaction commit  
- saveAndFlush() → immediately writes to database  

In most cases, save() is sufficient.

---

## What happens if we skip save()?

Without calling `save()`, a new entity will not be stored in the database, i.e.,  the changes to the entity will NOT be persisted to the database.

`save()` tells JPA/Hibernate to persist the entity so that it can be inserted or updated in the database.

`save()` ensures that the entity state is synchronized with the database.

---

## How does JPA decide INSERT vs UPDATE?

JPA decides based on the entity’s ID:

- If ID is null → INSERT  
- If ID exists → UPDATE  

Hibernate internally determines the entity state and generates the appropriate SQL.

---

## Entity Lifecycle (JPA)

An entity goes through different states in JPA:

- **Transient**
  - Created using `new`
  - Not associated with persistence context
  - Not saved in database

- **Persistent**
  - After calling `save()`
  - Managed by Hibernate (inside persistence context)
  - Changes are automatically tracked and synchronized with database

- **Detached**
  - No longer tracked (after persistence context/session ends)
  - Changes will NOT be automatically persisted unless reattached

This lifecycle helps Hibernate determine whether to perform INSERT or UPDATE operations.

---

## Hibernate CRUD with Session

Although most Spring Boot applications use `JpaRepository`, Hibernate's `Session` can perform CRUD operations directly.

### Save Entity

`session.save(entity)` schedules a new entity to be persisted. The actual INSERT is executed when the transaction is committed or the session is flushed.

Example:

```java
session.save(student);
```

This schedules an `INSERT` operation, which is executed when the transaction is committed.

---

### Fetch Entity by ID

`session.get()` retrieves an entity using its primary key.

Example:

```java
Student student = session.get(Student.class, id);
```

Behavior:

- Returns the entity if found
- Returns `null` if no matching record exists
- Immediately queries the database
- Immediately hits the database (unlike lazy-loading methods such as getReference())

---

## Session.get() vs JpaRepository.findById()

| Session.get() | JpaRepository.findById() |
|---------------|--------------------------|
| Returns entity or `null` | Returns `Optional<T>` |
| Hibernate API | Spring Data JPA API |
| Requires explicit null handling | Encourages safer handling using Optional |

### Interview Tip

In modern Spring Boot applications, `findById()` is generally preferred because it integrates with Spring Data JPA and promotes null-safe programming using `Optional`.

`Session.get()` is mainly encountered when working directly with Hibernate APIs or maintaining legacy applications.

---

## Hibernate Update and Delete

### Updating an Entity

A common update flow using Hibernate is:

1. Fetch the existing entity using `session.get()`
2. Modify the required fields
3. Commit the transaction

Example:

```java
Student student = session.get(Student.class, id);

student.setName("Akhil");
student.setAge(24);
```

Since the entity is already managed by the persistence context, Hibernate automatically detects the changes and issues an `UPDATE` statement when the transaction is committed.

---

### Partial Update vs Full Update

Two common update approaches are:

- **Partial Update**
  - Only the required fields are modified.
  - Existing values of other fields remain unchanged.

- **Full Update**
  - The entire object is replaced with new values.
  - Commonly associated with HTTP `PUT` requests.

### Interview Tip

Although `PUT` traditionally represents a full resource replacement, many real-world APIs update only selected fields while still exposing a `PUT` endpoint.

---

### Deleting an Entity

Hibernate deletes an entity using:

```java
session.remove(entity);
```

or

```java
session.delete(entity);
```

The entity must first be managed by the current persistence context.

Typical flow:

1. Retrieve the entity
2. Delete the entity
3. Commit the transaction

---

### CRUD Flow Using Hibernate Session

Create → `session.save()`

Read → `session.get()`

Update → Modify managed entity → Commit transaction

Delete → `session.remove()` / `session.delete()`

---
  
## What does findById() do?

findById() is a method provided by JpaRepository.

- Fetches a record using its primary key (id)
- Returns Optional<Entity>

Example:
taskRepository.findById(id)

If record exists:
→ Optional contains the entity  

If not:
→ Optional is empty  

In the project, we use:

.orElseThrow(() -> new TaskNotFoundException(id))

This ensures:
- If task exists → return it
- If not → throw custom exception

---

## Why Optional is used in findById()

- Avoids null values  
- Forces explicit handling of missing data  
- Prevents NullPointerException  
- Makes API more expressive  

---

## Why use map() with Optional?

map() transforms the value inside an Optional if present.

- Avoids explicit null checks  
- Makes code cleaner and more readable  

---

## updateTask Flow

- findById() returns Optional  
- map() updates entity if present  
- save() persists changes (UPDATE if ID exists)  
- orElseThrow() handles not found case  

---

## What does findAll() do?

findAll() retrieves all records from the database table and returns them as a List of entities.

---

## What does delete() do?

delete() is provided by JpaRepository.

It removes the given entity from the database.

---

## What does deleteById() do?

deleteById() deletes a record from the database using its primary key.

---

## What does existsById() do?

existsById() checks whether a record exists for the given ID and returns true or false.

---

## Entity Relationships

Relational databases often contain tables that are connected to each other.

JPA and Hibernate model these connections using entity relationships.

The most common relationship types are:

- One-to-One
- One-to-Many
- Many-to-One
- Many-to-Many

These relationships are represented using JPA annotations and allow Hibernate to automatically manage foreign key associations between tables.

---

## One-to-One Relationship

One record in Table A is associated with exactly one record in Table B.

Example:

- Person ↔ Passport
- User ↔ Profile

Typically implemented using `@OneToOne`.

---

## One-to-Many Relationship

One record in Table A can be associated with multiple records in Table B.

Example:

- One Department → Many Employees
- One Customer → Many Orders

Typically implemented using `@OneToMany`.

---

### @JoinColumn

`@JoinColumn` specifies the foreign key column used to establish a relationship between two entities.

It is placed on the **owning side** of the relationship—the entity whose table contains the foreign key.

Example:

```java
@OneToOne
@JoinColumn(name = "passport_id")
private Passport passport;
```

In this example, the `passport_id` column is created in the current entity's table and references the primary key of the `Passport` table.

### Interview Tip

If you need to determine where `@JoinColumn` should be placed, ask:

**Which table should contain the foreign key?**

The entity whose table stores the foreign key is the owning side and should contain `@JoinColumn`.

---

## Many-to-One Relationship

Many records in one table are associated with a single record in another table.

Example:

- Many Employees → One Department
- Many Orders → One Customer

Typically implemented using `@ManyToOne`.

---

## Many-to-Many Relationship

Multiple records in both tables can be associated with each other.

Example:

- Students ↔ Courses
- Users ↔ Roles

Typically implemented using `@ManyToMany` and a join table.

---

### Interview Tip

Choose the relationship based on the business domain.

Hibernate uses these mappings to automatically manage foreign keys and generate the required SQL joins.

---

## Spring MVC / Request Processing

---

## What is DispatcherServlet?

DispatcherServlet is the front controller in Spring MVC.

- Receives all incoming HTTP requests  
- Uses HandlerMapping to find the correct controller  
- Uses HandlerAdapter to execute the controller method  

It also handles:
- Data binding  
- Validation  
- Response conversion  

--- 
  
## What is HandlerAdapter?

HandlerAdapter is responsible for executing the controller method identified by HandlerMapping.

---

## What is HttpMessageConverter?

HttpMessageConverter is responsible for converting:

- HTTP request body → Java objects  
- Java objects → HTTP response  

It uses libraries like Jackson for JSON conversion.

---

## Validation vs JSON Parsing Failure

### Invalid JSON

- Occurs during HttpMessageConverter (Jackson)
- Exception: HttpMessageNotReadableException
- DTO is NOT created
- Returns 400 Bad Request

---

### Validation Failure

- Occurs after DTO creation
- Triggered by @Valid
- Exception: MethodArgumentNotValidException
- DTO is created but invalid
- Returns 400 Bad Request

---

## Why use ResponseEntity?

ResponseEntity is used to control:

- HTTP status code  
- Headers  
- Response body  

It allows sending proper responses like:
- 200 OK  
- 201 Created  
- 404 Not Found  

---

## @RequestBody vs @PathVariable vs @RequestParam (Quick Summary)

- @RequestBody
  - Used to read JSON from request body

  - Converted to DTO using HttpMessageConverter (Jackson)
  - Supports validation using @Valid

- @PathVariable
  - Used to extract values from URL path
  - Example: /tasks/{id}

- @RequestParam
  - Used to read query parameters
  - Example: /tasks?completed=true

### Key Difference:

- @RequestBody → Body data (JSON)
- @PathVariable → URL path
- @RequestParam → Query parameters

---

## PUT vs PATCH (API Design)

- PUT
  - Used to update the entire resource
  - Replaces all fields

- PATCH
  - Used to partially update a resource
  - Only modifies specified fields

### In my project:

- PUT is used for updating tasks
- Entire task object is updated

### Key Insight:

Choosing between PUT and PATCH depends on whether full or partial updates are required.

---

## Architecture

--- 
  
## Why DTO is Preferred Over Entity

DTO (Data Transfer Object) is used instead of Entity in API communication.

### Reasons:

- Prevents exposing internal database structure  
- Decouples API from database  
- Allows custom request/response formats  
- Improves security and maintainability  

### Key Idea:

Entity → database layer  
DTO → API layer

---

## Controller Design Best Practices

A controller should be thin and focused only on handling HTTP-related concerns.

### Responsibilities of Controller:

- Accept HTTP requests
- Validate input using DTOs
- Call Service layer
- Return appropriate HTTP response

### What SHOULD NOT be in Controller:

- Business logic
- Database operations
- Entity manipulation

### Why?

- Improves separation of concerns
- Makes code easier to maintain and test
- Keeps API layer independent of business logic

### Flow Reminder:

Controller → Service → Repository → Database

---

## Spring Core

---
  
## Spring IoC (Inversion of Control)

Inversion of Control (IoC) is a design principle where the responsibility of creating and managing objects is transferred from application code to the Spring container.

Instead of manually creating objects using `new`, Spring creates and manages them for us.

### Benefits

* Reduces coupling between classes
* Improves maintainability
* Simplifies dependency management

---

## Dependency Injection (DI)

Dependency Injection is a technique where dependencies are provided to a class instead of the class creating them itself.

Spring automatically injects the required dependencies using the IoC container.

### Benefits

* Better testability
* Reduced coupling
* Easier maintenance

---

## What is a Spring Bean?

A Spring Bean is an object that is created, managed, and maintained by the Spring IoC container.

Examples:

* Controllers
* Services
* Repositories

---

## Why Constructor Injection is Preferred

Constructor Injection is the recommended way of injecting dependencies.

### Advantages

* Dependencies are mandatory
* Easier unit testing
* Prevents null dependencies
* Promotes immutability

Example:

```java
@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }
}
```

---

## Common Spring Stereotype Annotations

### @Component

Marks a class as a Spring Bean.

### @Service

Marks a Service layer class and registers it as a Spring Bean.

### @Repository

Marks a Repository layer class and enables persistence-related exception translation.

### @Controller / @RestController

Marks a Controller class responsible for handling HTTP requests.

---
  
## Bean Scopes

Bean scope determines how many instances Spring creates for a bean.

### Singleton

Default scope.

Only one bean instance is created and shared across the application.

Multiple classes requesting the bean receive the same instance.

### Prototype

A new bean instance is created every time it is requested.

### Why Singleton is Default

- Lower memory usage
- Better performance
- Suitable for stateless services
  
---

## Singleton vs Prototype

| Feature                      | Singleton  | Prototype        |
| ---------------------------- | ---------- | ---------------- |
| Instances Created            | One        | New each request |
| Memory Usage                 | Lower      | Higher           |
| Performance                  | Better     | Slightly lower   |
| Suitable for Stateless Beans | Yes        | Yes              |
| Suitable for Stateful Beans  | Usually No | Yes              |

---

## When to Use Prototype Scope

Prototype scope is useful when:

* Each request needs a fresh object
* The bean stores state
* Object creation cost is low

Examples:

* Temporary processing objects
* User-specific state holders
* Workflow objects

---

### Request Scope

A new bean instance is created for every HTTP request.

Useful when data should live only for the duration of a single request.

---

### Session Scope

A bean instance is created per user session.

Data remains available across multiple requests from the same user.

---

## Stateful vs Stateless Beans

### Stateless Bean

A stateless bean does not store request-specific data in instance variables.

Characteristics:

* No shared mutable state
* Thread-safe
* Better scalability
* Suitable for Singleton scope
* Common for Service and Repository classes

Examples:

- Service classes
- Repository classes

A `TaskService` that processes requests without storing user-specific information is a stateless bean.

---

### Stateful Bean

A stateful bean stores data in instance variables.

Example:

```java
private String currentUser;
```

Characteristics:

* Maintains state between method calls
* Can cause shared mutable state issues
* Requires careful scope selection

Potential Problem:

* Shared mutable state
* Race conditions in concurrent requests
* Data leakage between users

Short interview answer for problem:

A Singleton bean is shared by all requests.

If it stores mutable request-specific state, multiple users may overwrite each other's data, causing race conditions and inconsistent behavior.

Example:
If a Singleton bean stores user-specific data:

* User A sets currentUser = "Akhil"
* User B sets currentUser = "John"

Both users share the same Singleton bean instance, so values can overwrite each other unexpectedly.

### Recommendation

Stateful beans should generally avoid Singleton scope.

If state must be maintained, Prototype scope or request-scoped beans may be more appropriate.

---

### Key Interview Point

Singleton beans should generally be stateless.

Stateful objects are usually better suited for Prototype, Request, or Session scopes depending on the requirement.
