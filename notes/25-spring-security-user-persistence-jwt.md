# Spring Security - User Persistence & JWT Authentication

## Overview

This section moves from in-memory authentication toward persistent user management.

Instead of hardcoding usernames and passwords inside the security configuration, user credentials can be stored and managed through the application's database.

The section covers:

- Persistent user credentials
- User and role entities
- Database-backed authentication
- Remember Me
- JWT authentication
- Related authentication concepts and implementation

---

## User Persistence

In-memory authentication is useful for learning, testing, and simple examples, but real applications generally need user credentials to be stored persistently.

Instead of defining users directly inside the security configuration, the application can provide an API through which users can be created and stored in the database.

This allows authentication to use users that exist in the application's persistent data store.

---

## User and Role Relationship

A user can have one or more roles, and the same role can be assigned to multiple users.

This represents a **many-to-many relationship**:

**User ↔ Role**

For example:

- A user can have roles such as `NORMAL` and `ADMIN`.
- A role such as `ADMIN` can belong to multiple users.

A many-to-many relationship can be represented using a join table in the database.

### Conceptual Structure

**User**

- id
- username
- password
- roles

**Role**

- id
- name

**User_Role**

- user_id
- role_id

The join table connects users with their assigned roles.

---

## Database-Backed Authentication

The purpose of moving from in-memory users to persistent users is to allow Spring Security to authenticate users whose credentials are stored in the application's database.

The general flow becomes:

**Login Request → Authentication → User Lookup → Database → Credential Verification → Authenticated User**

The exact implementation using entities, repositories, `UserDetailsService`, password encoding, and related components will be covered as the section progresses.

---

## Section Progress

Covered so far:

- Moving from hardcoded users toward persistent users
- User entity
- Role entity
- User–Role many-to-many relationship
- Join table concept

Upcoming topics:

- Database-backed authentication implementation
- UserDetails / UserDetailsService
- Password encoding
- Remember Me
- JWT authentication

---

## User and Role Entities

User credentials can be persisted in the database using a `User` entity.

Roles can also be represented as a separate entity.

A user can have multiple roles, and the same role can be assigned to multiple users.

This represents a many-to-many relationship:

**User ↔ Role**

A join table stores the relationship between users and roles.

Conceptually:

    User
      ↓
    User_Role
      ↑
    Role

The `User` entity maintains a collection of roles:

    private Set<Role> roles = new HashSet<>();

The many-to-many relationship can be mapped using `@JoinTable`.

    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(
            name = "user",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "role",
            referencedColumnName = "id"
        )
    )
    private Set<Role> roles = new HashSet<>();

Here:

- `@JoinTable` defines the join table used to connect users and roles.
- `joinColumns` defines the column that references the `User` entity.
- `inverseJoinColumns` defines the column that references the `Role` entity.

The exact table and column names depend on the application's entity mapping.

---

## Custom UserDetailsService

Spring Security requires a way to load user information during authentication.

A custom `UserDetailsService` can be created by implementing the `UserDetailsService` interface.

    public class CustomUserDetailsService
            implements UserDetailsService {
        // ...
    }

The custom service loads a user based on the username.

The repository provides a method such as:

    Optional<User> findByUsername(String username);

This allows the authentication process to retrieve the user's information from the database.

---

## Implementing UserDetails

The persistent `User` entity can implement Spring Security's `UserDetails` interface.

    public class User implements UserDetails {
        // ...
    }

By implementing `UserDetails`, the application's persisted user can provide the information required by Spring Security during authentication.

The required methods from `UserDetails` are implemented inside the `User` class.

This connects the application's user data with Spring Security's authentication process.

---

## Granted Authorities

Spring Security uses `GrantedAuthority` to represent the authorities granted to an authenticated user.

The roles stored with the user can be converted into `SimpleGrantedAuthority` objects.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
            .map(role -> new SimpleGrantedAuthority(
                role.getRoleName()
            ))
            .collect(Collectors.toList());
    }

The general flow is:

**Stored roles → Stream roles → Convert to GrantedAuthority → Return authorities**

This allows Spring Security to use roles stored in the database for authorization.

---

## AuthenticationManager Configuration

With database-backed authentication, user details are loaded through the custom `UserDetailsService`.

The previous in-memory `UserDetailsService` bean is no longer required.

An `AuthenticationManager` bean is configured for authentication.

A `PasswordEncoder` bean is still required to encode and verify passwords.

---

## User Registration

A public registration API can be provided so that users can be created through the application instead of being hardcoded inside the security configuration.

For example:

    POST /register

The registration endpoint must be allowed without requiring prior authentication.

Request-level security configuration can allow access to the registration endpoint while requiring authentication for protected endpoints.

Conceptually:

**`/register` → Public access**

**Other protected endpoints → Authentication required**

---

## Password Encoding Before Persistence

Passwords should be encoded before being stored in the database.

    String encodedPassword =
        bCryptPasswordEncoder.encode(
            userRequest.getPassword()
        );

    user.setPassword(encodedPassword);

The encoded password is stored instead of the plain-text password.

`BCryptPasswordEncoder` can be used to perform the encoding.

### Key Idea

**Plain-text password → BCrypt encoding → Store encoded password**

---

## Database-Backed Authentication Flow

The application can now use persisted user information during authentication.

A simplified registration flow is:

**Registration Request**

↓

**Encode Password**

↓

**Save User and Roles**

↓

**Database**

During authentication:

**Login Request**

↓

**Authentication Manager**

↓

**Custom UserDetailsService**

↓

**Load User by Username**

↓

**Database**

↓

**Verify Credentials**

↓

**Authenticated User with Authorities**

---

## Remember Me

The Remember Me feature allows a user to remain authenticated even after the normal HTTP session expires.

### Basic Flow

```text
Login
↓
Session is created
↓
JSESSIONID cookie is stored
↓
Session expires
↓
Remember Me information is used
↓
User can remain authenticated
```

The normal session is identified using the `JSESSIONID` cookie.

Remember Me becomes relevant after the normal authenticated session is no longer available.

### Custom Login Form

To use Remember Me, a custom login form can be created with a **Remember Me** checkbox.

The custom login page can be created using:

- `login.html`
- Thymeleaf

The user can select the Remember Me option while logging in.

### Spring Security Configuration

Form-based login must be enabled in the security configuration.

```java
.formLogin()
```

Remember Me functionality can then be configured in the security configuration.

### Testing Remember Me

Instead of waiting for the session to expire, the session can be removed manually for testing.

For example:

- Log in with Remember Me selected.
- Delete the `JSESSIONID` cookie.
- Verify how the Remember Me functionality behaves after the normal session cookie is removed.

Browser tools or cookie-management extensions can be used during testing.