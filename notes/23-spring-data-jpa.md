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
- deleteInBatch()

It is the repository interface used in most Spring Boot applications.

---

## Interview Tip

Most Spring Boot projects directly extend JpaRepository because it already includes CRUD, pagination, sorting, and JPA-specific operations.