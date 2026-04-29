# Core Backend Concepts

## What does save() do?

The save() method is provided by JpaRepository. It uses JPA and Hibernate to persist the entity into the database.

- If the entity is new → INSERT operation  
- If the entity already exists → UPDATE operation  

Internally, Hibernate decides whether to perform insert or update based on the entity state.

---

## What does persist mean?

Persist means saving an entity permanently into the database.

---

## What is HandlerAdapter?

HandlerAdapter is responsible for executing the controller method identified by HandlerMapping.

---

## What is @Entity?

@Entity marks a class as a JPA entity and maps it to a database table.

It tells Hibernate to treat this class as a table and its fields as columns.

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

## What happens if we skip save()?

Without calling save(), changes to the entity will NOT be persisted to the database.

save() ensures that the entity state is synchronized with the database.

---

## How does JPA decide INSERT vs UPDATE?

JPA decides based on the entity’s ID:

- If ID is null → INSERT  
- If ID exists → UPDATE  

Hibernate internally determines the entity state and generates the appropriate SQL.

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

## What does delete() do?

delete() is provided by JpaRepository.

It removes the given entity from the database.

---

## What does findAll() do?

findAll() retrieves all records from the database table and returns them as a List of entities.

---

## What does deleteById() do?

deleteById() deletes a record from the database using its primary key.

---

## What does existsById() do?

existsById() checks whether a record exists for the given ID and returns true or false.

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

## save() vs saveAndFlush()

- save() → may delay DB write until transaction commit  
- saveAndFlush() → immediately writes to database  

In most cases, save() is sufficient.

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