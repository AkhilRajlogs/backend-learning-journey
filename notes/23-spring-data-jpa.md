# Spring Data JPA

## What is Spring Data JPA?

Spring Data JPA is a Spring project that simplifies database access by reducing boilerplate code.

It builds on top of JPA and provides repository interfaces for performing CRUD operations without writing SQL.

---

## Repository Hierarchy

Spring Data JPA provides several repository interfaces.

Common hierarchy:

Repository
    ↓
CrudRepository
    ↓
PagingAndSortingRepository
    ↓
JpaRepository

Each interface extends the previous one and provides additional functionality.

---

## CrudRepository

CrudRepository provides basic CRUD operations such as:

- save()
- findById()
- findAll()
- delete()
- deleteById()
- existsById()

---

## PagingAndSortingRepository

Extends CrudRepository.

Adds:

- pagination
- sorting

---

## JpaRepository

Extends PagingAndSortingRepository.

Provides additional JPA-specific operations such as:

- flush()
- saveAndFlush()

It is the repository interface used in most Spring Boot applications.

---

## Spring Data JPA Queries

Spring Data JPA provides different ways to query data without writing database access code manually.

One important approach is **derived queries**, where Spring Data JPA creates the query based on the repository method name.

### Derived Queries

A derived query is a repository method whose name describes the query to be executed.

Spring Data JPA parses the method name and generates the corresponding query.

Example:

    public interface TaskRepository extends JpaRepository<Task, Long> {

        List<Task> findByCompleted(boolean completed);

        List<Task> findByTitle(String title);
    }

### Common Derived Query Keywords

Spring Data JPA supports several keywords for building derived queries.

Examples:

    findByTitle(String title)

    findByCompleted(boolean completed)

    findByAgeGreaterThan(int age)

    findByAgeLessThan(int age)

    findByAgeBetween(int min, int max)

    findByTitleContaining(String text)

    findByTitleStartingWith(String text)

    findByTitleEndingWith(String text)

    findByCompletedAndPriority(boolean completed, String priority)

    findByCompletedOrPriority(boolean completed, String priority)

    findByTitleOrderByCreatedAtDesc(String title)

The method name determines the conditions and ordering of the generated query.

### How Derived Queries Work

For example:

    List<Task> findByCompleted(boolean completed);

Spring Data JPA interprets:

    findBy → query operation

    Completed → entity field

    boolean completed → value used in the condition

Conceptually, this represents a query similar to:

    SELECT * FROM task WHERE completed = ?;

The developer does not need to write the SQL manually.

### Derived Query vs Explicit Query

**Derived Query**

- Query is generated from the repository method name.
- Useful for simple queries.
- Reduces boilerplate code.
- Can become difficult to read when method names become very long.

**Explicit Query**

- Query is written explicitly using mechanisms such as `@Query`.
- Useful for complex queries.
- Gives more control over the query.

---

## JPQL (Java Persistence Query Language)

JPQL is a query language provided by JPA for querying **entities and their fields** rather than database tables and columns.

Example:

    @Query("SELECT t FROM Task t WHERE t.completed = :completed")
    List<Task> findByCompleted(@Param("completed") boolean completed);

Here:

- `Task` refers to the entity class.
- `completed` refers to an entity field.
- JPQL is written using entity-oriented concepts rather than database table/column names.

Hibernate translates the JPQL query into the appropriate SQL for the underlying database.

### JPQL vs SQL

**JPQL**

- Works with entities and entity fields.
- Database-independent at the query-language level.
- JPA/Hibernate translates it into SQL.

**SQL**

- Works directly with tables and columns.
- Database-specific syntax may be involved.
- Sent directly to the database.

### Interview Tip

JPQL is useful when the query is more complex than a simple derived query but should still remain database-independent.

---

## Native Query

A native query is a query written directly in the database's SQL language.

Example:

    @Query(value = "SELECT * FROM task WHERE completed = :completed", nativeQuery = true)
    List<Task> findCompletedTasks(@Param("completed") boolean completed);

Unlike JPQL, a native query works directly with database tables and columns.

### When to Use Native Queries

Native queries can be useful when:

- The required query is difficult to express using JPQL.
- Database-specific SQL features are required.
- Existing SQL needs to be reused.

### JPQL vs Native Query

| Feature | JPQL | Native Query |
|---|---|---|
| Works with | Entities and fields | Tables and columns |
| Query language | JPQL | SQL |
| Database independent | Generally yes | Usually no |
| Translated by Hibernate | Yes | SQL is sent directly |
| Useful for | JPA-oriented queries | Complex or database-specific queries |

### Interview Tip

Prefer derived queries for simple queries, JPQL for more flexible entity-based queries, and native SQL when database-specific functionality or complex SQL is required.

---

## Query Approach — Quick Comparison

Spring Data JPA provides multiple ways to retrieve data:

1. **Derived Query**
   - Query is generated from the repository method name.
   - Best for simple queries.

2. **JPQL**
   - Query is written using entities and their fields.
   - Useful for more complex entity-based queries.

3. **Native Query**
   - Query is written directly in SQL.
   - Useful for database-specific or complex SQL requirements.

### Interview Tip

A useful progression to remember is:

**Simple → Derived Query**

**More flexible → JPQL**

**Database-specific / complex SQL → Native Query**

Derived queries are convenient when the required query can be clearly expressed through the repository method name.

For complex queries, an explicit query using `@Query` is generally more appropriate.

Most Spring Boot projects directly extend JpaRepository because it already includes CRUD, pagination, sorting, and JPA-specific operations.

Spring Data JPA reduces boilerplate by allowing developers to define repository interfaces and derive queries from method names instead of writing common database-access code manually.
