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

Spring Security can be customized using a configuration class.

A security configuration class can use:

- `@Configuration`

- `@EnableWebSecurity`

A `SecurityFilterChain` bean can then be used to configure how HTTP requests should be secured.

A basic configuration example is:

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(
                HttpSecurity http
        ) throws Exception {

            http
                .csrf().disable()
                .authorizeHttpRequests()
                    .anyRequest().authenticated()
                .and()
                .formLogin();

            return http.build();
        }

    }

This configuration demonstrates:

- Disabling CSRF protection for the example.

- Requiring authentication for all requests.

- Enabling form-based login.

Conceptually:

Request

↓

Spring Security Filter Chain

↓

Authorization Rules

↓

Authentication if required

↓

Request Allowed or Denied

### Logout

Spring Security provides a default logout endpoint:

`/logout`

---

## In-Memory Authentication

Spring Security can be configured with users stored in memory for simple examples and testing.

A `UserDetailsService` bean can be used to provide user information to Spring Security.

The user can be created using Spring Security's user builder and returned as part of the in-memory authentication configuration.

Passwords should be encoded rather than stored as plain text.

The implementation details of `UserDetailsService`, user creation, and password encoding will be covered further in the module.

---

## Custom Username and Password

Spring Security can be configured with custom users stored in memory.

A `UserDetailsService` bean can provide user details to Spring Security.

Users can be created using Spring Security's `User` builder.

Example:

    @Bean
    public UserDetailsService user() {

        UserDetails user = User.builder()
            .username("Tony")
            .password(passwordEncoder().encode("password"))
            .roles("NORMAL")
            .build();

        UserDetails user2 = User.builder()
            .username("Steve")
            .password(passwordEncoder().encode("nopassword"))
            .roles("NORMAL")
            .build();

        return new InMemoryUserDetailsManager(user, user2);
    }

### Password Encoding

Passwords can be encoded before being stored in the in-memory user configuration.

A `PasswordEncoder` bean can be provided to Spring Security.

Example:

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

### In-Memory User Configuration

`InMemoryUserDetailsManager` stores the configured users in memory.

In this example:

- `User.builder()` creates user details.

- `username()` specifies the username.

- `password()` specifies the encoded password.

- `roles()` assigns roles to the user.

- `InMemoryUserDetailsManager` manages the configured users.

- `PasswordEncoder` is used to encode passwords.

The details of password encoders and authentication mechanisms will be covered further in the module.

---

## HTTP Basic Authentication

Spring Security supports HTTP Basic Authentication as an alternative to form-based login.

A complete configuration example is:

    @Configuration
    @EnableWebSecurity
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

Conceptually:

Request

↓

Authentication required

↓

HTTP Basic credentials provided

↓

Spring Security authenticates the user

↓

Request Allowed or Denied

With HTTP Basic Authentication, the client sends credentials with the HTTP request.

It is commonly useful for simple APIs, testing, and learning authentication flows.

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

## HTTP Security Status Codes

Spring Security commonly uses HTTP status codes to indicate the result of a security check.

### 401 Unauthorized

`401 Unauthorized` generally indicates that authentication is required or the supplied authentication credentials are not valid.

The client has not successfully authenticated.

### 403 Forbidden

`403 Forbidden` indicates that the request is understood, but the authenticated user does not have sufficient permission to access the requested resource.

### Key Difference

**401 → Authentication problem**

**403 → Authorization / permission problem**

---

## Key Idea

Spring Security can control both:

- **How users authenticate**, such as form-based or HTTP Basic authentication.
- **What authenticated users can access**, using authorization rules and roles.

For example:

**Authentication → Who are you?**

**Authorization → Are you allowed to access this endpoint?**

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