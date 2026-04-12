# Core Backend Concepts

## What does save() do?

The save() method is provided by JpaRepository. It uses JPA and Hibernate to persist the entity into the database. If the entity is new, it performs an insert; otherwise, it performs an update and returns the saved entity.

---

## What does persist mean?

Persist means saving an entity permanently into the database.

---

## What is HandlerAdapter?

HandlerAdapter is responsible for executing the controller method identified by HandlerMapping.