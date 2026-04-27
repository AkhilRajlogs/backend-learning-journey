# Core Backend Concepts

## What does save() do?

The save() method is provided by JpaRepository. It uses JPA and Hibernate to persist the entity into the database. If the entity is new, it performs an insert; otherwise, it performs an update and returns the saved entity.
Internally, Hibernate decides whether to perform insert or update based on the entity state.

---

## What does persist mean?

Persist means saving an entity permanently into the database.

---

## What is HandlerAdapter?

HandlerAdapter is responsible for executing the controller method identified by HandlerMapping.

---

## What is @Entity?

@Entity marks a class as a JPA entity and maps it to a database table. Baiscally, it tells Hibernate to map this class to a database table.

---

## What is JPA?

JPA (Java Persistence API) is a specification that defines how Java objects are mapped to database tables.

---

## What is Hibernate?

Hibernate is the implementation of JPA. It handles ORM and generates SQL queries automatically.

---

## What is @Id?

@Id marks the primary key of an entity and is used to uniquely identify each record in the database.  

---

## What is HttpMessageConverter?

HttpMessageConverter is responsible for converting HTTP request body to Java objects and Java objects to HTTP response using libraries like Jackson.

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

## What does findById() do?

findById() is a method provided by JpaRepository.

It is used to fetch a record from the database using its primary key (id).

It returns an Optional<Entity>.

Example:
taskRepository.findById(id)

If the record exists:
→ Optional contains the entity

If not:
→ Optional is empty

In the project, we use:
.orElseThrow(() -> new TaskNotFoundException(id))

This ensures:
- If task exists → return it
- If not → throw custom exception

## Why Optional is used in findById()

findById() returns Optional to avoid null values.

It forces the developer to explicitly handle the case when data is not found, preventing NullPointerException.

It also makes the API more expressive by clearly indicating that the value may or may not be present.
  
---  
  
## What happens if we skip save()?

Without calling save(), changes to the entity are not guaranteed to be persisted to the database.

save() ensures that the entity state is synchronized with the database.

---

## How does JPA decide INSERT vs UPDATE?

JPA decides based on the entity’s ID:

- If ID is null → INSERT operation
- If ID exists → UPDATE operation

Hibernate internally determines the entity state and generates the appropriate SQL.

---

## Why use map() with Optional?

map() is used to transform the value inside an Optional if present, without explicit null checks.

It helps in writing cleaner and more readable code compared to using if-else for null handling.

## updateTask Flow

- findById() returns Optional
- map() updates entity if present
- save() persists changes (UPDATE if ID exists)
- orElseThrow() handles not found case

---

## What is DispatcherServlet?

DispatcherServlet is the front controller in Spring MVC.

It receives all incoming HTTP requests from the server and coordinates the request lifecycle.

It uses HandlerMapping to find the correct controller method and HandlerAdapter to execute it.

It also handles request processing tasks like data binding, validation, and response conversion before sending the response back to the client.

---
  
## What does delete() do?

delete() is provided by JpaRepository.

It removes the given entity from the database.

## What does findAll() do?

findAll() is provided by JpaRepository.

It retrieves all records from the database table and returns them as a List of entities.

## What does deleteById() do?

deleteById() deletes a record from the database using its primary key.

## What does existsById() do?

existsById() is provided by JpaRepository.

It checks whether a record exists in the database for the given ID and returns true or false.

## Why use ResponseEntity?

ResponseEntity is used to control HTTP response details like status code, headers, and body.

It allows sending proper responses such as 200 OK, 201 Created, or 404 Not Found.