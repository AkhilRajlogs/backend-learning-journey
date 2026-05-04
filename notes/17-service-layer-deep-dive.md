# Service Layer Deep Dive (Task Manager API)

## Role of Service Layer

The Service layer contains business logic and acts as a bridge between Controller and Repository.

It ensures:
- Separation of concerns
- Reusability of logic
- Clean controller design

---

## Example: addTask()

Method:

public Task addTask(TaskDTO dto)

### Flow:

1. Create Entity from DTO

- Converts API data → database model

2. Call repository

taskRepository.save(task);

- Triggers JPA/Hibernate
- Performs INSERT if ID is null

3. Return saved entity

- Contains generated ID
- Represents latest DB state

---

## Key Responsibilities

- Business logic execution
- DTO → Entity conversion
- Calling repository methods
- Logging important operations

---

## Important Notes

Service layer should NOT handle:

- HTTP annotations (@RequestBody, @PathVariable)
- ResponseEntity
- HTTP status codes

Service must remain independent of the web layer.

---

## Interview Summary

Service layer contains business logic and interacts with the repository while keeping the controller thin.