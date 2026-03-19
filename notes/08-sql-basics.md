# SQL Basics

## What is SQL?

SQL (Structured Query Language) is used to interact with relational databases.

It allows us to:
- Store data
- Retrieve data
- Update data
- Delete data

---

## Why SQL is Important for Backend

In backend development, SQL is used to:

- Persist application data
- Query data efficiently
- Support business logic
- Work with ORMs like JPA/Hibernate

---

## Basic Commands

### SELECT

Used to fetch data from a table.

SELECT * FROM tasks;

Fetch specific columns:

SELECT id, title FROM tasks;

---

### INSERT

Used to add new data.

INSERT INTO tasks (title, completed)
VALUES ('Learn Spring Boot', false);

---

### UPDATE

Used to modify existing data.

UPDATE tasks
SET completed = true
WHERE id = 1;

---

### DELETE

Used to remove data.

DELETE FROM tasks
WHERE id = 1;

---

## Table Example

Example table: tasks

| id | title              | completed |
|----|--------------------|----------|
| 1  | Learn Spring Boot  | false    |
| 2  | Build API          | true     |

---

## Notes

- SQL works with relational databases like PostgreSQL
- Tables store structured data
- Each row represents a record
- Each column represents a field

---

## Next Step (Learning Roadmap)

- Connect Spring Boot to PostgreSQL
- Learn JDBC basics
- Use Spring Data JPA
- Replace in-memory storage with database