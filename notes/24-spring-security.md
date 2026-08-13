# Spring Security

## Introduction

Spring Security is used to secure Spring applications by controlling who can access the application and what they are allowed to do.

---

## Authentication vs Authorization

### Authentication

Authentication answers:

**"Who are you?"**

It is the process of verifying the identity of a user or client.

### Authorization

Authorization answers:

**"What are you allowed to do?"**

It determines what resources or operations an authenticated user is permitted to access.

### Key Difference

| Authentication | Authorization |
|---|---|
| Verifies identity | Determines permissions |
| "Who are you?" | "What can you do?" |
| Happens before authorization | Depends on the authenticated identity |

### Simple Example

A user logs into an application with a username and password.

- Verifying that the credentials belong to that user → **Authentication**
- Checking whether that user is allowed to access an admin endpoint → **Authorization**

---

## Features of Spring Security

Spring Security provides several features for securing Spring applications.

### Authentication

Verifies the identity of a user or client.

### Authorization

Determines what an authenticated user is allowed to access or perform.

### Session Management

Manages user sessions after authentication.

It helps control how authenticated sessions are created, maintained, and handled.

### Remember Me

Allows an application to remember an authenticated user across sessions, so the user does not need to authenticate again every time.

### CSRF Protection

CSRF (Cross-Site Request Forgery) protection helps prevent malicious requests from being performed on behalf of an authenticated user.

### Two-Factor Authentication

Two-factor authentication adds an additional verification step beyond the primary authentication method.

This improves security by requiring two forms of verification.

### OAuth and OpenID Connect

Spring Security can integrate with authentication and authorization protocols such as OAuth and OpenID Connect.

A common example is:

**"Sign in with Google"**

* **OAuth** → commonly used for authorization
* **OpenID Connect (OIDC)** → provides authentication and identity information

### Integration with Other Frameworks

Spring Security can integrate with other frameworks and components used in Spring applications.

---

## Authentication Mechanisms

Spring Security supports different authentication mechanisms depending on the application's requirements.

### HTTP Basic Authentication

HTTP Basic Authentication sends the username and password with each request using Base64 encoding.

It is simple to configure and is commonly used for basic authentication scenarios and testing.

### Form-Based Authentication

Form-based authentication allows users to authenticate through a login form.

The application receives the user's credentials through the form and authenticates the user.

### JWT Authentication

JWT (JSON Web Token) authentication uses tokens to represent an authenticated user's identity.

After successful authentication, the server can issue a JWT that the client sends with subsequent requests.

JWT-based authentication is commonly used in modern REST APIs.

### OAuth and OpenID Connect

OAuth is commonly used for authorization and delegated access.

OpenID Connect (OIDC) builds on OAuth and provides authentication and identity information.

A common example is:

**"Sign in with Google"**

### LDAP

LDAP (Lightweight Directory Access Protocol) can be used for authentication and access to organizational directory resources.

It is commonly associated with enterprise environments where user accounts and access information are managed centrally.

---

## Key Idea

Spring Security provides security features beyond simply checking usernames and passwords.

It supports different authentication mechanisms and security features depending on the application's requirements.
