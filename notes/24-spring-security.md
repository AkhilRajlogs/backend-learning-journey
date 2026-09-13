# Spring Security

## Introduction

Spring Security is used to secure Spring applications by controlling who can access the application and what they are allowed to do.

---

## How to Use This Note

This note covers **Week 1 — Section 1: Spring Security Foundations and Basic Authentication**.

The goal is not to memorize every Spring Security class or annotation.

The goal is to understand:

- what each security component does
- when a component is required
- how the configuration evolved during the section
- which annotations belong to which classes
- how to identify the minimum configuration required by a coding problem
- how authentication and authorization fit together

### Section 1 Learning Progression

The concepts were introduced progressively:

```text
Spring Security basics
        ↓
In-memory users
        ↓
HTTP Basic Authentication
        ↓
URL-level authorization
        ↓
Method-level authorization
        ↓
Form Login
```

Each step adds a different capability.

**Important:** A coding problem does not necessarily require every feature covered in the section.

Always identify what the problem is asking for and configure only the components required for that requirement.

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

---

## Spring Security Authentication Flow

A simplified authentication flow is:

Request

↓

Authentication Filter

↓

Authentication Manager

↓

Authentication Provider

↓

Authentication / credential verification

↓

Authenticated `Authentication`

> **Coding-problem note:** This is the internal conceptual authentication flow. You do not necessarily declare an `AuthenticationManager` or `AuthenticationProvider` bean yourself. For simple `httpBasic()` / `formLogin()` problems, Spring Security can configure the required authentication infrastructure automatically. `AuthenticationManager` becomes especially important when authentication is performed programmatically, such as custom login or JWT authentication.

### Authentication Filter

The authentication filter intercepts the incoming authentication request and creates an authentication token containing the supplied credentials.

For example, with form-based authentication, the submitted username and password are used to create a username-password authentication token.

### Authentication Manager

The `AuthenticationManager` is responsible for coordinating authentication.

It receives an authentication request and delegates it to an appropriate `AuthenticationProvider`.

The `AuthenticationManager` can be exposed as a bean using Spring Security's authentication configuration:

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

The `AuthenticationManager` can then be injected where programmatic authentication is required.

For example, authentication can be initiated using:

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            username,
            password
        )
    );

Conceptually:

Username + Password

↓

`UsernamePasswordAuthenticationToken`

↓

`AuthenticationManager.authenticate()`

↓

`AuthenticationProvider`

↓

Credential Verification

↓

Authenticated `Authentication`

If authentication succeeds, the returned `Authentication` object represents the authenticated user.

### Authentication Provider

The `AuthenticationProvider` performs the actual authentication for a particular type of authentication.

It receives the authentication token and performs the necessary credential verification.

The provider's `authenticate()` method contains the authentication logic.

### Simplified Flow

The overall idea can be remembered as:

**Request → Filter → Authentication Manager → Authentication Provider → Authentication**

This is a simplified conceptual flow; the exact components involved can vary depending on the authentication mechanism.

---

## Spring Security Configuration

Spring Security configuration defines **how the application should authenticate users and control access to requests**.

For Section 1, the main security configuration is usually built around:

- `SecurityConfig` class
- `SecurityFilterChain`
- `UserDetailsService`
- `PasswordEncoder`

However, **not every problem requires all of these beans**.

### SecurityConfig Class

A typical configuration class is:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        // security configuration

        return http.build();
    }
}
```

### What Each Annotation Does

| Annotation | Purpose | Usually required? |
|---|---|---|
| `@Configuration` | Marks the class as a Spring configuration class | Yes |
| `@EnableWebSecurity` | Enables Spring Web Security configuration | Common in course examples |
| `@Bean` | Registers a method's return value as a Spring bean | Required on bean-producing methods |

### SecurityFilterChain

`SecurityFilterChain` defines how incoming HTTP requests are processed by Spring Security.

For example:

```java
@Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http
) throws Exception {

    http
        .authorizeHttpRequests()
            .anyRequest().authenticated()
        .and()
        .httpBasic();

    return http.build();
}
```

This configuration means:

1. Requests enter the Spring Security filter chain.
2. Requests must be authenticated.
3. HTTP Basic Authentication is used.
4. If authentication succeeds, the request can continue.

### Minimum Beans Depend on the Problem

Do not assume that every `SecurityConfig` must contain every possible Spring Security bean.

For example:

#### HTTP Basic + In-Memory Users

Typically requires:

```text
SecurityFilterChain
UserDetailsService / InMemoryUserDetailsManager
PasswordEncoder
```

#### HTTP Basic Without Custom In-Memory Users

The application may not need a custom `UserDetailsService` bean if user details are being supplied through another configured mechanism.

#### Method-Level Security

Requires method-level security to be enabled, for example:

```java
@EnableGlobalMethodSecurity(prePostEnabled = true)
```

and the methods that need method-level authorization can use:

```java
@PreAuthorize("hasRole('ADMIN')")
```

#### Database Authentication

Requires additional persistence-related components, which are covered in Note 25.

### Bean Responsibility Map

A useful way to remember the responsibilities is:

| Component | Responsibility |
|---|---|
| `SecurityFilterChain` | Defines HTTP security and authorization rules |
| `UserDetailsService` | Provides user information to Spring Security |
| `InMemoryUserDetailsManager` | Stores user details in memory |
| `PasswordEncoder` | Encodes passwords and verifies encoded passwords during authentication |
| `AuthenticationManager` | Coordinates programmatic authentication |
| `AuthenticationProvider` | Performs authentication for a particular authentication mechanism |

**Important:** The presence of a class or bean in Spring Security does not mean it must be explicitly declared in every application.

Use a bean when your implementation requires that responsibility.

### Example: Section 1 Basic Configuration

A common Section 1 configuration is:

```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
            .authorizeHttpRequests()
                .anyRequest().authenticated()
            .and()
            .httpBasic();

        return http.build();
    }

    @Bean
    public UserDetailsService users() {

        UserDetails user = User.builder()
            .username("Tony")
            .password(passwordEncoder().encode("password"))
            .roles("NORMAL")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

This is a **complete example**, not a mandatory template for every problem.

The required configuration depends on what the problem asks you to implement.

### What About `EntityManager`?

`EntityManager` is related to **JPA/database persistence**.

It is not required simply because Spring Security is being used.

For example:

```text
HTTP Basic
+
InMemoryUserDetailsManager
+
@PreAuthorize
```

does not require an `EntityManager`.

`EntityManager` becomes relevant when the application performs persistence operations through JPA.

The persistence-related Spring Security implementation is covered in **Note 25**.

### Logout

Spring Security provides a default logout endpoint:

`/logout`

Logout configuration can also be customized when required.

Do not add logout-specific configuration to a problem unless the problem requires customized logout behaviour.

---

## In-Memory Authentication

In-memory authentication stores user details in the application's memory instead of retrieving users from a database.

It is useful for:

- learning Spring Security
- testing
- small demonstrations
- coding exercises

The main component is:

```java
InMemoryUserDetailsManager
```

It implements `UserDetailsService` and provides configured users to Spring Security.

### Creating Users

Users can be created using Spring Security's `User` builder:

```java
UserDetails user = User.builder()
    .username("Tony")
    .password(passwordEncoder().encode("password"))
    .roles("NORMAL")
    .build();
```

Multiple users can be supplied:

```java
@Bean
public UserDetailsService users() {

    UserDetails user1 = User.builder()
        .username("Tony")
        .password(passwordEncoder().encode("password"))
        .roles("NORMAL")
        .build();

    UserDetails user2 = User.builder()
        .username("Steve")
        .password(passwordEncoder().encode("nopassword"))
        .roles("ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user1, user2);
}
```

### What Each Part Does

| Code | Responsibility |
|---|---|
| `User.builder()` | Creates Spring Security user details |
| `.username()` | Defines the username |
| `.password()` | Defines the encoded password |
| `.roles()` | Assigns roles |
| `InMemoryUserDetailsManager` | Stores and retrieves users from memory |
| `UserDetailsService` | Spring Security interface used to retrieve user details |

### PasswordEncoder

Passwords should not be stored as plain text.

A `PasswordEncoder` can be configured as a bean:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

The password can then be encoded when creating the user:

```java
.password(passwordEncoder().encode("password"))
```

### Important Coding-Problem Rule

If a problem specifies:

> Create custom users using `InMemoryUserDetailsManager`

think:

```text
InMemoryUserDetailsManager
        +
User.builder()
        +
PasswordEncoder
```

Then add the required authentication and authorization configuration separately.

Do not add database repositories, entities, `EntityManager`, or JWT components unless the problem specifically requires them.

### In-Memory Authentication Flow

```text
Configured User
      ↓
InMemoryUserDetailsManager
      ↓
UserDetailsService
      ↓
Authentication Provider
      ↓
Authentication
```

The important idea is that the user information is available **in memory**, so no database lookup is required.

---

## HTTP Basic Authentication

HTTP Basic Authentication is an authentication mechanism where the client sends a username and password with the HTTP request.

The credentials are Base64-encoded in the `Authorization` header.

> Base64 is an encoding mechanism, not encryption. HTTPS is required to protect credentials in transit.

### Enabling HTTP Basic

```java
http
    .authorizeHttpRequests()
        .anyRequest().authenticated()
    .and()
    .httpBasic();
```

The `.httpBasic()` configuration tells Spring Security to use HTTP Basic Authentication for authentication.

### Typical Section 1 Combination

HTTP Basic is commonly combined with in-memory users:

```text
HTTP Request
      ↓
HTTP Basic credentials
      ↓
Spring Security Filter Chain
      ↓
UserDetailsService
      ↓
Authentication
      ↓
Authorization
      ↓
Controller
```

### Coding-Problem Recognition

If a problem says:

- use HTTP Basic authentication
- create users in memory
- authenticate using username/password

the basic components are:

```text
SecurityFilterChain
        +
httpBasic()
        +
UserDetailsService / InMemoryUserDetailsManager
        +
PasswordEncoder
```

Additional authorization rules are added only when the problem requires them.

### HTTP Basic vs Form Login

| HTTP Basic | Form Login |
|---|---|
| Credentials are sent through HTTP authentication headers | Credentials are submitted through a login form |
| Common for APIs/testing | Common for browser-based applications |
| Browser/client handles authentication prompt or credentials | Application can provide a login page |
| Configured using `.httpBasic()` | Configured using `.formLogin()` |

Both are authentication mechanisms. They do not themselves determine which roles can access which endpoints.

---

## Authorization with Ant Matchers

Spring Security can restrict access to specific endpoints based on the user's role.

Ant-style path matching can be used to define which requests require particular roles.

A complete request-level authorization configuration can be written as:

    http
        .authorizeHttpRequests()
            .antMatchers("/hotel/create").hasRole("ADMIN")
            .antMatchers("/hotel/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        .and()
        .httpBasic();

The authorization rules are defined inside `authorizeHttpRequests()`.

The earlier rules define requirements for specific requests.

    .antMatchers("/hotel/create").hasRole("ADMIN")

    .antMatchers("/hotel/**").hasRole("ADMIN")

The following rule can define the default requirement for requests that do not match the earlier rules:

    .anyRequest().authenticated()

The `.and()` call then continues the configuration outside the authorization configuration.

Conceptually:

`authorizeHttpRequests()`

↓

Specific request rules

↓

Default rule for remaining requests

↓

`.and()`

↓

Configure another security feature

The complete example:

    http
        .authorizeHttpRequests()
            .antMatchers("/hotel/create").hasRole("ADMIN")
            .antMatchers("/hotel/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        .and()
        .httpBasic();

means:

- `/hotel/create` requires the `ADMIN` role.

- `/hotel/**` requires the `ADMIN` role.

- Any remaining requests require authentication.

- HTTP Basic Authentication is enabled.

### Path Matching

`/hotel/create`

- Matches the specific `/hotel/create` endpoint.

- Only users with the `ADMIN` role are allowed.

`/hotel/**`

- Matches endpoints under `/hotel/`.

- Only users with the `ADMIN` role are allowed.

The order and specificity of authorization rules matter when defining multiple request-matching rules.

---

## Method-Level Security

Spring Security can also apply authorization rules directly at the controller or service method level.

With method-level security, authorization can be specified on individual methods.

Web security and method-level security can be configured together.

For example:

    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(
                HttpSecurity http
        ) throws Exception {

            http
                .authorizeHttpRequests()
                    .anyRequest().authenticated()
                .and()
                .httpBasic();

            return http.build();
        }

    }

In this configuration:

- `@EnableWebSecurity` enables web security configuration.

- `SecurityFilterChain` configures HTTP request security.

- `@EnableGlobalMethodSecurity(prePostEnabled = true)` enables method-level security features such as `@PreAuthorize`.

These security mechanisms can be used together.

### @PreAuthorize

`@PreAuthorize` can be placed directly on a controller or service method to specify who is allowed to execute it.

Example:

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/hotel")
    public Hotel createHotel(...) {

        // ...

    }

Only users with the `ADMIN` role can execute this method.

Another method can restrict access to users with a different role:

    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping("/hotel")
    public List<Hotel> getHotels(...) {

        // ...

    }

### Does Every Method Need `@PreAuthorize`?

No.

`@PreAuthorize` is used only when **method-level authorization is required**.

For example:

```java
@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/hotel")
public Hotel createHotel(...) {
    // ...
}
```

This means the method requires the `ADMIN` role.

Another method might not need `@PreAuthorize` if:

- it is intentionally accessible to all authenticated users
- authorization is already handled appropriately at the request level
- the problem does not require method-level authorization

### When Should I Use It?

Think of the requirement first:

```text
"Secure /admin/** for ADMIN users"
        ↓
Request-level authorization
        ↓
SecurityFilterChain
```

Whereas:

```text
"Only ADMIN users can execute this method"
        ↓
Method-level authorization
        ↓
@PreAuthorize
```

Method-level security is especially useful when authorization needs to be associated directly with a particular method rather than only with its URL.

### `@PreAuthorize` Is Authorization, Not Authentication

`@PreAuthorize` does not authenticate the user.

Authentication establishes:

```text
Who is the user?
```

`@PreAuthorize` evaluates:

```text
Is this authenticated user allowed to execute this method?
```

### Request-Level vs Method-Level Authorization

**Request-Level Security**

Authorization rules are applied based on the request path.

Example:

    http
        .authorizeHttpRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        .and()
        .httpBasic();

The authorization decision is based on the request path.

**Method-Level Security**

Authorization rules are applied directly to individual methods.

Example:

    @PreAuthorize("hasRole('ADMIN')")
    public void adminOperation() {

        // ...

    }

The authorization decision is applied directly to the method.

### Key Idea

Request-level and method-level security can provide different layers of authorization.

Conceptually:

`@EnableWebSecurity`

↓

Enables web security configuration

↓

`SecurityFilterChain`

↓

Configures HTTP request security

---

`@EnableGlobalMethodSecurity`

↓

Enables method-level security

↓

`@PreAuthorize`

↓

Applies authorization to individual methods

---

## Modern Spring Security Configuration

Some Spring Security examples use older configuration APIs such as:

- `@EnableGlobalMethodSecurity`

- `.antMatchers()`

- `.and()`

These examples can still appear in existing courses and projects that use older Spring Security versions.

Newer Spring Security versions commonly use newer configuration APIs.

### Modern Method-Level Security

The modern alternative to:

`@EnableGlobalMethodSecurity(prePostEnabled = true)`

is:

    @EnableMethodSecurity

For example:

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    public class SecurityConfig {

        // ...

    }

### Modern Request Matching

The modern alternative to:

    .antMatchers("/admin/**")

is commonly:

    .requestMatchers("/admin/**")

### Modern Configuration Style

Newer Spring Security versions commonly use lambda-based configuration.

For example:

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(
                HttpSecurity http
        ) throws Exception {

            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated()
                )
                .httpBasic();

            return http.build();
        }

    }

Conceptually:

Older style:

`antMatchers()`

↓

`anyRequest()`

↓

`.and()`

↓

Next configuration section

Modern style:

`requestMatchers()`

↓

`anyRequest()`

↓

Configuration lambda ends

↓

Next configuration method

The exact syntax depends on the Spring Security version used by the application.

When following an existing course or project, the examples should remain consistent with the Spring Security version used in that project.

---

## Annotation Map for Coding Problems

Spring Security problems can involve several different classes.

Do not assume that every class needs every annotation.

### Security Configuration Class

Typical:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
}
```

If method-level security is required in the course's older configuration style:

```java
@EnableGlobalMethodSecurity(prePostEnabled = true)
```

### Controller

A controller may use normal Spring MVC annotations such as:

```java
@RestController
@RequestMapping("/hotel")
```

HTTP method mappings:

```java
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
```

If method-level authorization is required:

```java
@PreAuthorize("hasRole('ADMIN')")
```

`@PreAuthorize` is **not required on every controller method**.

### DTO

A DTO does not need Spring Security annotations simply because the application uses Spring Security.
Security annotations should be added because of a specific security requirement, not simply because the class exists in a secured application.

It may use Lombok annotations such as:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
```

depending on what the DTO requires.

Security configuration and DTO boilerplate are separate concerns.

### Entity / Model

An entity uses persistence-related annotations when it is a JPA entity, for example:

```java
@Entity
@Table(name = "hotel")
```

These are related to database persistence, not HTTP Basic authentication itself.

Entity-related configuration becomes important when persistent users are introduced in **Note 25**.

### Quick Rule

Think in terms of responsibility:

| Class | Main concern | Typical annotations/components |
|---|---|---|
| `SecurityConfig` | Security configuration | `@Configuration`, `@EnableWebSecurity`, `@Bean` |
| Controller | HTTP/API layer | `@RestController`, mappings |
| Secured method | Authorization | `@PreAuthorize` when required |
| DTO | Data transfer | Lombok / validation as required |
| Entity | Database persistence | JPA annotations |
| Repository | Database access | Spring Data repository |
| Security user service | User lookup | `UserDetailsService` |

### `@EnableGlobalMethodSecurity`

The course uses:

```java
@EnableGlobalMethodSecurity(prePostEnabled = true)
```

For this module, the important part is:

```text
prePostEnabled = true
        ↓
Enables pre/post method security annotations
        ↓
Allows annotations such as @PreAuthorize
```

The annotation has other configuration options in Spring Security, but they are not required for the Section 1 coding problems covered here.

For the current problems, remember:

```java
@EnableGlobalMethodSecurity(prePostEnabled = true)
```

when the problem specifically requires `@PreAuthorize` / pre-post method security.

### Modern Equivalent

In newer Spring Security versions, the commonly used replacement is:

```java
@EnableMethodSecurity
```

Therefore:

```text
Course / older configuration
        ↓
@EnableGlobalMethodSecurity(prePostEnabled = true)

Modern configuration
        ↓
@EnableMethodSecurity
```

Use the syntax that matches the Spring Security version used by the project or coding problem.

---

## HTTP Security Status Codes

Spring Security can result in different HTTP status codes depending on whether authentication or authorization fails.

### 401 Unauthorized

`401 Unauthorized` generally indicates that authentication is required or authentication credentials were not successfully provided/accepted.

Think:

**"The client is not successfully authenticated."**

### 403 Forbidden

`403 Forbidden` generally indicates that the user is authenticated but does not have sufficient permission to access the requested resource.

Think:

**"The user is authenticated, but is not allowed to do this."**

### Key Difference

```text
401 → Authentication problem

403 → Authorization / permission problem
```

### Do I Need to Set HTTP Status Codes Manually?

No.

You do **not** normally add a status-code annotation to every controller method simply because Spring Security is being used.

For example, you do not need to write:

```java
@ResponseStatus(...)
```

on every method.

Spring MVC and Spring Security can determine appropriate responses based on the request processing and security outcome.

Explicit response-status configuration is only needed when the API's requirements call for a specific status code.

---

## Key Idea

Spring Security can control both:

- **How users authenticate**, such as form-based or HTTP Basic authentication.
- **What authenticated users can access**, using authorization rules and roles.

For example:

**Authentication → Who are you?**

**Authorization → Are you allowed to access this endpoint?**

---

## Section 1 Coding Problem Guide

When solving a Spring Security problem, first identify **what the problem is asking for**.

Do not start by writing every security component you remember.

### Step 1 — Identify the User Store

Ask:

**Where are the users stored?**

If the problem says:

> users are created in memory

use:

```text
InMemoryUserDetailsManager
```

If the problem says:

> users are stored in a database

move to the persistent-user approach covered in Note 25.

### Step 2 — Identify the Authentication Mechanism

Ask:

**How should the user authenticate?**

| Requirement | Authentication |
|---|---|
| HTTP Basic | `.httpBasic()` |
| Login page | `.formLogin()` |
| JWT token | JWT authentication |
| Database credentials | Persistent user authentication |

### Step 3 — Identify Authorization Requirements

Ask:

**Who is allowed to access what?**

If the requirement is based on URL/path:

```java
.authorizeHttpRequests()
    .antMatchers("/admin/**").hasRole("ADMIN")
```

If the requirement is specifically on a method:

```java
@PreAuthorize("hasRole('ADMIN')")
```

### Step 4 — Identify Required Beans

For a basic in-memory authentication problem, think:

```text
SecurityFilterChain
        ↓
HTTP security configuration

InMemoryUserDetailsManager implements UserDetailsService
        ↓
Stores users in memory
        ↓
Provides users through UserDetailsService

PasswordEncoder
        ↓
Encodes and verifies passwords
```

Add other components only when the problem requires them.

### Step 5 — Identify Required Class Annotations

Do not annotate every class with security annotations.

Think by responsibility:

```text
SecurityConfig
→ Security configuration annotations

Controller
→ Spring MVC annotations

DTO
→ DTO/Lombok/validation annotations as required

Entity
→ JPA annotations

Secured method
→ @PreAuthorize only when method-level authorization is required
```

### Common Section 1 Combinations

#### Combination 1 — In-Memory + HTTP Basic

```text
InMemoryUserDetailsManager
        +
PasswordEncoder
        +
SecurityFilterChain
        +
httpBasic()
```

#### Combination 2 — In-Memory + HTTP Basic + URL Authorization

```text
In-memory users
        +
HTTP Basic
        +
authorizeHttpRequests()
        +
role-based request matchers
```

#### Combination 3 — In-Memory + HTTP Basic + Method Security

```text
In-memory users
        +
HTTP Basic
        +
@EnableGlobalMethodSecurity(prePostEnabled = true)
        +
@PreAuthorize(...)
```

#### Combination 4 — Form Login

```text
UserDetailsService
        +
SecurityFilterChain
        +
formLogin()
```

Additional configuration depends on the problem.

### Common Mistakes

**Mistake 1: Adding every bean you have seen**

Do not add `EntityManager`, repositories, JWT filters, or database entities to an in-memory authentication problem.

**Mistake 2: Adding `@PreAuthorize` everywhere**

Use it only when method-level authorization is required.

**Mistake 3: Confusing authentication with authorization**

```text
Authentication → Who are you?

Authorization → What are you allowed to do?
```

**Mistake 4: Treating HTTP status annotations as mandatory**

Spring Security does not require every controller method to have an explicit HTTP status annotation.

**Mistake 5: Mixing course versions**

The course may use:

```java
@EnableGlobalMethodSecurity(prePostEnabled = true)
.antMatchers(...)
```

Newer Spring Security versions commonly use:

```java
@EnableMethodSecurity
.requestMatchers(...)
```

Understand the concept first, then use the syntax required by the project's Spring Security version.

---

## Section 1 Summary

This section introduced the fundamentals of Spring Security and basic security configuration.

Covered topics include:

- Authentication vs Authorization
- Spring Security features
- Authentication mechanisms
- Authentication flow
- Security configuration using `SecurityFilterChain`
- Form-based authentication
- In-memory authentication
- Password encoding with `PasswordEncoder`
- HTTP Basic Authentication
- Request-level authorization using Ant-style matchers
- Method-level authorization using `@PreAuthorize`
- HTTP security status codes such as `401 Unauthorized` and `403 Forbidden`

The section established the basic idea of:

**Authentication → Verify identity**

**Authorization → Control access**

More advanced topics such as persistent users, database-backed authentication, JWT authentication, and remember-me functionality are covered in the next section.