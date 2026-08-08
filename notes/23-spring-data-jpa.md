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

### Interview Tip

Derived queries are convenient when the required query can be clearly expressed through the repository method name.

For complex queries, an explicit query using `@Query` is generally more appropriate.

---

## Interview Tip

Most Spring Boot projects directly extend JpaRepository because it already includes CRUD, pagination, sorting, and JPA-specific operations.

Spring Data JPA reduces boilerplate by allowing developers to define repository interfaces and derive queries from method names instead of writing common database-access code manually.
