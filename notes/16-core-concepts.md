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

@Entity marks a class as a JPA entity. It marks a class as a JPA entity and maps it to a database table. Baiscally, it tells Hibernate to map this class to a database table.

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