# Core Backend Concepts

## What does save() do?

The save() method is provided by JpaRepository. It uses JPA and Hibernate to persist the entity into the database. If the entity is new, it performs an insert; otherwise, it performs an update and returns the saved entity.

---

## What does persist mean?

Persist means saving an entity permanently into the database.

---

## What is HandlerAdapter?

HandlerAdapter is responsible for executing the controller method identified by HandlerMapping.

---

## What is @Entity?

@Entity marks a class as a JPA entity. It tells Hibernate to map this class to a database table.

---

## What is JPA?

JPA (Java Persistence API) is a specification that defines how Java objects are mapped to database tables.

---

## What is Hibernate?

Hibernate is the implementation of JPA. It handles ORM and generates SQL queries automatically.

---

## What is HandlerAdapter?

HandlerAdapter is responsible for executing the controller method identified by HandlerMapping.

---

## What does persist mean?

Persist means saving an entity permanently into the database.

---

## What is @Id?

@Id marks the primary key of an entity and is used to uniquely identify each record in the database.  