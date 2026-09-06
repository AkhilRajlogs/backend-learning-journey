# Spring Security - User Persistence & JWT Authentication

## Overview

This section moves from in-memory authentication toward persistent user management.

Instead of hardcoding usernames and passwords inside the security configuration, user credentials can be stored and managed through the application's database.

The section covers:

- Persistent user credentials
- User and role entities
- User–role relationships
- Database-backed authentication
- Custom `UserDetailsService`
- Password encoding
- Registration
- Remember Me
- JWT authentication

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

## User and Role Entities

User credentials can be persisted in the database using a `User` entity.

Roles can also be represented as a separate entity.

A user can have multiple roles, and the same role can be assigned to multiple users.

This represents a many-to-many relationship:

**User ↔ Role**

A join table stores the relationship between users and roles.

Conceptually:

```text
User
  ↓
User_Role
  ↑
Role
```

The `User` entity maintains a collection of roles:

```java
    private Set<Role> roles = new HashSet<>();
```

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

```java
    public class CustomUserDetailsService
            implements UserDetailsService {
        // ...
    }
```

The custom service loads a user based on the username.

The repository provides a method such as:

```java
    Optional<User> findByUsername(String username);
```
This allows the authentication process to retrieve the user's information from the database.

---

## Implementing UserDetails

The persistent `User` entity can implement Spring Security's `UserDetails` interface.

```java
    public class User implements UserDetails {
        // ...
    }
```

By implementing `UserDetails`, the application's persisted user can provide the information required by Spring Security during authentication.

The required methods from `UserDetails` are implemented inside the `User` class.

This connects the application's user data with Spring Security's authentication process.

---

## Granted Authorities

Spring Security uses `GrantedAuthority` to represent the authorities granted to an authenticated user.

The roles stored with the user can be converted into `SimpleGrantedAuthority` objects.

```java
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
            .map(role -> new SimpleGrantedAuthority(
                role.getRoleName()
            ))
            .collect(Collectors.toList());
    }
```

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

```text
    POST /register
```
The registration endpoint must be allowed without requiring prior authentication.

Request-level security configuration can allow access to the registration endpoint while requiring authentication for protected endpoints.

Conceptually:

**`/register` → Public access**

**Other protected endpoints → Authentication required**

---

## Password Encoding Before Persistence

Passwords should be encoded before being stored in the database.

```java
    String encodedPassword =
        bCryptPasswordEncoder.encode(
            userRequest.getPassword()
        );

    user.setPassword(encodedPassword);
```
The encoded password is stored instead of the plain-text password.

`BCryptPasswordEncoder` can be used to perform the encoding.

### Key Idea

**Plain-text password → BCrypt encoding → Store encoded password**

---

## Database-Backed Authentication Flow

The application can now use persisted user information during authentication.

A simplified registration flow is:

```text
Registration Request
        ↓
Encode Password
        ↓
Save User and Roles
        ↓
Database
```

During authentication:

```text
Login Request
        ↓
Authentication Manager
        ↓
Custom UserDetailsService
        ↓
Load User by Username
        ↓
Database
        ↓
Verify Credentials
        ↓
Authenticated User with Authorities
```

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

---

## Remember Me Implementation

Spring Security can use a custom login page to provide a Remember Me option.

### Thymeleaf Dependency

Thymeleaf can be added as a dependency to create and render server-side HTML templates.

A custom login page can be created at:

```text
src/main/resources/templates/login.html
```

The `login.html` page contains:

- Username field
- Password field
- Remember Me checkbox
- Submit button

The custom login page can be returned by a controller.

```java
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
```

The `login()` method returns the `login` view, which corresponds to `login.html`.

---

### Custom Login Form

The custom login form allows the user to provide authentication credentials and optionally select the Remember Me option.

Conceptually:

```text
Username + Password
        +
Remember Me selected
        ↓
Custom Login Form
        ↓
Spring Security Authentication
```

In this implementation, Spring Security uses the conventional remember-me mechanism.

When Remember Me is enabled, a remember-me cookie is created in addition to the normal JSESSIONID session cookie.

The custom login page can be created as a Thymeleaf template.

A typical location for the template is:

```text
src/main/resources/templates/login.html
```

The login form contains:

- Username field
- Password field
- Remember Me checkbox
- Login button

Example `login.html` template:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Login</title>
</head>

<body>

<div class="container-fluid text-center">

    <form th:action="@{/login}"
          method="post"
          style="max-width: 350px; margin: 0 auto;">

        <div class="border border-secondary p-3 rounded">

            <p>Enter Login Credential</p>

            <p>
                <input type="text"
                       name="username"
                       class="form-control"
                       placeholder="Username"
                       required
                       autofocus />
            </p>

            <p>
                <input type="password"
                       name="password"
                       class="form-control"
                       placeholder="Password"
                       required />
            </p>

            <p>
                <input type="checkbox"
                       name="remember-me" />
                &nbsp;Remember Me
            </p>

            <p>
                <input type="submit"
                       value="Login"
                       class="btn btn-primary" />
            </p>

        </div>

    </form>

</div>

</body>

</html>
```

This template can also be used as a reference when creating similar custom login pages for Spring Security applications or coding problems.

The important connection with Remember Me is the checkbox:

```html
<input type="checkbox" name="remember-me" />
```

The `remember-me` name allows Spring Security's Remember Me functionality to recognize the option submitted by the login form.

The form submits the credentials to:

```text
POST /login
```

which is the conventional login processing endpoint used by Spring Security form login.

---

### UserDetailsService

The database-backed `UserDetailsService` can be injected into the security configuration.

```java
@Autowired
UserDetailsService userDetailsService;
```

The `UserDetailsService` is used to load user details when Spring Security needs to authenticate or restore the authenticated user.

---

### Remember Me Security Configuration

Remember Me must be configured together with the application's request authorization and authentication configuration.

The registration endpoint can be made publicly accessible using `permitAll()`.

Remember Me can then be configured with the `UserDetailsService`.

A typical configuration for an application using database-backed authentication and Remember Me can include:

```java
http
    .csrf().disable()
    .authorizeHttpRequests()
        .antMatchers("/user/register", "/login")
        .permitAll()
        .anyRequest()
        .authenticated()
    .and()
    .rememberMe()
        .userDetailsService(userDetailsService)
    .and()
    .formLogin()
        .loginPage("/login")
        .permitAll()
    .and()
    .logout()
        .deleteCookies("remember-me");
```

This configuration demonstrates several important points:

- `csrf().disable()` disables CSRF protection for this application configuration. This was required by the assessment application's test setup.
- `authorizeHttpRequests()` defines which requests require authentication.
- `/user/register` is publicly accessible so a new user can register without already being authenticated.
- `/login` is publicly accessible so users can reach the login page.
- `anyRequest().authenticated()` requires authentication for other requests.
- `rememberMe()` enables Remember Me functionality.
- `userDetailsService(userDetailsService)` allows Spring Security to use the database-backed `UserDetailsService` when restoring authentication.
- `formLogin()` enables form-based login and specifies the custom login page.
- `logout().deleteCookies("remember-me")` removes the Remember Me cookie during logout.

The overall security configuration can therefore be understood as:

```text
CSRF disabled
        ↓
Request Authorization
        ↓
/user/register and /login → Public
        ↓
Other requests → Authentication required
        ↓
Remember Me enabled
        ↓
Form Login enabled
        ↓
Logout removes remember-me cookie
```

The exact configuration syntax depends on the Spring Security version used by the application. The example above follows the configuration style used in the application implementation.

---

### Testing Remember Me

A browser normally stores the `JSESSIONID` cookie for the current authenticated session.

When Remember Me is selected, a separate `remember-me` cookie is also created.

The behavior can be tested as follows:

1. Log in with the Remember Me checkbox selected.
2. Verify that `JSESSIONID` and `remember-me` cookies are present.
3. Delete the `JSESSIONID` cookie.
4. Leave the `remember-me` cookie unchanged.
5. Refresh or make another request.

Spring Security can use the Remember Me information to restore the user's authentication.

A new `JSESSIONID` is then created for the restored authenticated session.

The flow can be understood as:

```text
JSESSIONID deleted
        ↓
remember-me cookie remains
        ↓
Spring Security restores authentication
        ↓
New JSESSIONID is created
```

If the Remember Me checkbox is not selected:

```text
JSESSIONID deleted
        ↓
No Remember Me information available
        ↓
Authentication is no longer restored
        ↓
User must log in again
```

---

### Key Difference

```text
JSESSIONID
    ↓
identifies the current HTTP session

remember-me
    ↓
Allows Spring Security to restore authentication after the normal session is no longer available
```

### Key Idea

Remember Me does not replace normal session management.

A normal authenticated session uses `JSESSIONID`.

Remember Me provides a mechanism that can restore authentication when the normal session is no longer available.

---

## JSON Web Token (JWT)

JWT stands for **JSON Web Token**.

JWT is a compact token format for representing claims and securely transmitting them when appropriate signing, transport, and validation mechanisms are used.

A JWT can be created and signed by one application. Another application can verify that the token is authentic and has not been altered by validating its signature.

---

### JJWT Dependencies

A JWT implementation can be added using the **JJWT (Java JWT)** library.

For a Maven project, the required dependencies can be added to `pom.xml`:

```xml
<!-- JWT API -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.6</version>
</dependency>

<!-- JWT Implementation (Runtime Only) -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>

<!-- JWT Jackson Serializer (Runtime Only) -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>
```

The dependencies have different responsibilities:

- `jjwt-api` → provides the JWT API used by the application code.
- `jjwt-impl` → provides the JWT implementation and is required at runtime.
- `jjwt-jackson` → provides Jackson-based JSON serialization/deserialization support and is required at runtime.

The application code can therefore use the JJWT API while the implementation and JSON support are provided at runtime.

**Key idea:**

```text
jjwt-api
    ↓
JWT API used by application

jjwt-impl
    ↓
JWT implementation at runtime

jjwt-jackson
    ↓
Jackson JSON serialization support at runtime
```

---

### JWT Authentication and Authorization

JWTs are commonly used for authentication and authorization in web applications.

They allow a server to issue a signed token that can be presented by a client when accessing protected resources.

JWTs are commonly digitally signed, allowing the receiving application to verify that the token was issued by the expected party and has not been altered.

---

### JWT Structure

A JWT consists of three parts:

```text
Header
  ↓
Payload
  ↓
Signature

```  
The token is commonly represented as:

`Header.Payload.Signature`

---

### Header

The header contains information such as:

- The token type
- The signing algorithm used

The header is Base64URL-encoded.

---

### Payload

The payload contains the information, known as **claims**, carried by the token.

For example:

- Username
- Roles
- Other required claims or user information

The payload is Base64URL-encoded.

---
  
### Types of Claims

Claims are statements or pieces of information carried in the JWT payload.

There are three types of claims:

- **Registered Claims** → These are predefined claims that are recommended for common purposes. Examples include `iss` (issuer), `exp` (expiration time), and `sub` (subject).
- **Public Claims** → These claims can be defined for sharing information between parties using the token. They should be defined in the IANA JSON Web Token Registry or as a URI to avoid naming conflicts.
- **Private Claims** → These are custom claims created to share information between parties that agree to use them.

For example:

```json
{
    "iss": "your_issuer",
    "sub": "1234567890",
    "name": "John Doe",
    "email": "johndoe@example.com",
    "role": "user",
    "exp": 1645872000,
    "nbf": 1645795600,
    "iat": 1645795600,
    "jti": "a1b2c3d4e5f6g7h8i9j0"
}
```

Here:

* `iss` identifies the issuer of the token.
* `sub` identifies the subject of the token.
* `exp` represents the expiration time.
* `nbf` represents the time before which the token must not be accepted.
* `iat` represents the time at which the token was issued.
* `jti` provides a unique identifier for the token.
* Other fields such as `name`, `email`, and `role` can carry application-specific information.

The important idea is that **claims are pieces of information about the subject or other relevant information carried in the JWT payload**.

### Signature

The signature is used to verify the integrity and authenticity of the token.

Conceptually:

`Encoded Header + Encoded Payload + Secret Key → Signature`

The signature is generated using the encoded header, encoded payload, and the configured signing key or secret, depending on the signing algorithm.

The signature allows the receiving application to verify that the token has not been altered and was signed by the expected party.

---

### Key Idea

JWT is commonly understood as:

**Header → What type of token and algorithm are used**

**Payload → What information or claims the token carries**

**Signature → How the token's integrity and authenticity are verified**

A JWT can therefore be used to securely exchange signed information between two parties.

---

## How JWT Works

JWT-based authentication generally follows these steps:

1. **Authentication** → The user provides their credentials to the authentication server.
2. **Token Generation** → After successful authentication, the server creates and signs a JWT containing the required claims.
3. **Token Delivery** → The server sends the JWT to the client.
4. **Authorization** → The client includes the JWT when making requests to protected resources.
5. **Token Validation** → The server verifies the JWT signature and checks relevant claims such as expiration.
6. **Access Control** → If the token is valid and the user has the required authority, access to the protected resource is granted.

The overall flow can be represented as:

```text
Client
  ↓
Credentials
  ↓
Authentication Server
  ↓
JWT Generated & Signed
  ↓
JWT Sent to Client
  ↓
Client Sends JWT with Request
  ↓
Server Validates JWT
  ↓
Access Granted / Denied
```

Unlike session-based authentication, the server does not need to maintain a server-side session for each authenticated user when using a stateless JWT-based approach.

The JWT carries the information required to identify and authorize the user, while the server validates the token before allowing access to protected resources.


## JWT Use Cases

JWTs are commonly used in applications where a client needs to securely present authentication information to a server.

Common use cases include:

- **Single Sign-On (SSO)** → A JWT can be used to represent an authenticated user across multiple applications or services.
- **API Authentication** → A client can send a JWT with API requests to access protected endpoints.
- **Stateless Authentication** → The server can authenticate requests using the JWT without maintaining a server-side session for each user.

JWTs are especially useful in distributed systems and REST APIs because the token can be sent with requests independently of server-side session state.

---

## JWT Security Considerations

JWTs are signed tokens, but their payload is generally **not encrypted**. Therefore, the information stored in the payload should not be treated as secret.

### Do Not Store Sensitive Information

Do not store passwords, secret keys, or other highly sensitive information directly in the JWT payload.

The payload can be decoded by anyone who has access to the token. The signature helps detect tampering, but it does not make the payload confidential.

Only include the information required by the application.

---

### Use HTTPS

JWTs should be transmitted over **HTTPS**.

HTTPS protects the token while it is being transmitted between the client and server and helps prevent attackers from intercepting the token.

---

### Use Token Expiration

JWTs should have an appropriate expiration time using the `exp` claim.

Short-lived tokens reduce the period during which a stolen token can be used.

Applications that require longer-lived authentication can use mechanisms such as refresh tokens rather than making the access token valid for an unnecessarily long period.

---

### Validate the Token

The server should validate the JWT before accepting it.

Validation should include checking the token's signature and relevant claims, such as expiration time and other claims required by the application.

A token should be rejected if it is invalid, expired, or otherwise fails the application's validation rules.

---

The important idea is that **JWT security depends not only on signing the token, but also on protecting the token during transmission, limiting the information stored in it, using appropriate expiration times, and validating it correctly**.

---

## JWT Authentication Flow

JWT authentication can still use Spring Security's existing authentication components.

The general authentication flow remains similar:

```text
Authentication Request
        ↓
Authentication Filter
        ↓
Authentication Manager
        ↓
Authentication Process
        ↓
Authenticated User
```

The main difference is that JWT authentication uses a custom JWT authentication filter instead of relying only on the default authentication filter.

Conceptually:

```text
JWT Authentication Request
        ↓
JWT Authentication Filter
        ↓
Authentication Manager
        ↓
Authentication
        ↓
JWT Generated
        ↓
JWT Returned to Client
```

The client can then send the JWT with subsequent requests.

---

## Custom JWT Authentication Filter

Spring Security provides multiple filters.

For JWT authentication, a custom filter can be created to handle JWT-specific authentication logic.

The application can create a class such as:

JWTAuthenticationFilter

The custom filter can extend `OncePerRequestFilter`.

### OncePerRequestFilter

The custom `JWTAuthenticationFilter` can extend `OncePerRequestFilter`.

`OncePerRequestFilter` is commonly used for security filters that should execute once for each request during the normal request-processing flow.

Conceptually:

```text
Request
        ↓
JWTAuthenticationFilter
        ↓
doFilterInternal()
        ↓
JWT Authentication Logic
        ↓
Continue Filter Chain
```

The custom filter overrides the `doFilterInternal()` method.

```java
@Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
) throws ServletException, IOException {

    // Custom JWT authentication logic

}

```

The method provides access to:

- `HttpServletRequest` → the incoming request
- `HttpServletResponse` → the outgoing response
- `FilterChain` → the remaining filters in the Spring Security filter chain

The JWT authentication logic can be implemented inside this method as the JWT authentication implementation progresses.

---

## JWT Login API

JWT authentication begins with a login request containing the user's credentials.

A request object can represent the login data:

```java
public class JwtRequest {

    private String username;

    private String password;

    // getters and setters

}
```

The login request can then be authenticated using the `AuthenticationManager`:

```java
Authentication authentication =

    authenticationManager.authenticate(

        new UsernamePasswordAuthenticationToken(

            jwtRequest.getUsername(),

            jwtRequest.getPassword()

        )

    );
```

The authentication flow is:

```text
JwtRequest

    ↓

Username + Password

    ↓

UsernamePasswordAuthenticationToken

    ↓

AuthenticationManager

    ↓

AuthenticationProvider

    ↓

Credential Verification

    ↓

Authenticated Authentication
```

If authentication succeeds, the returned `Authentication` object represents the authenticated user.

---

## Generating and Returning the JWT

After successful authentication, the application can generate a JWT for the authenticated user.

A simplified login API can be structured as:

```java
@PostMapping("/login")

public ResponseEntity<JwtResponse> login(

        @RequestBody JwtRequest jwtRequest

) {

    Authentication authentication =

        authenticationManager.authenticate(

            new UsernamePasswordAuthenticationToken(

                jwtRequest.getUsername(),

                jwtRequest.getPassword()

            )

        );

    // Get authenticated user

    // Generate JWT

    // Return JWT

}
```

The JWT can then be returned to the client using a response object:

```java
public class JwtResponse {

    private String token;

    // constructor

    // getter

}
```

The complete login flow can be remembered as:

```text
Login Request

    ↓

Username + Password

    ↓

AuthenticationManager

    ↓

Credentials Verified

    ↓

Authenticated User

    ↓

Generate JWT

    ↓

Return JWT to Client
```

The client can use the returned JWT for subsequent authenticated requests.

The process of sending and validating the JWT on subsequent requests is handled separately.

---

## Sending the JWT with Subsequent Requests

After the login API successfully authenticates the user and returns a JWT, the client can use that JWT when making subsequent requests to protected endpoints.

The JWT is commonly sent in the `Authorization` HTTP header.

The standard format is:

```text
Authorization: Bearer <JWT>
```

For example:

```text
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

The `Bearer` prefix indicates that the client is presenting a bearer token for authentication.

The general flow becomes:

```text
Login Request
    ↓
Username + Password
    ↓
AuthenticationManager
    ↓
Credentials Verified
    ↓
JWT Generated
    ↓
JWT Returned to Client
    ↓
Client Stores JWT
    ↓
Client Sends JWT with Subsequent Requests
```

The client should include the JWT in the `Authorization` header when accessing protected endpoints.

---

## JWT Validation in the Custom Filter

The `JWTAuthenticationFilter` can inspect incoming requests to determine whether a JWT has been provided.

The filter can read the `Authorization` header from the incoming request.

Conceptually:

```java
String authorizationHeader =
        request.getHeader("Authorization");
```

The filter can then check whether the header contains a Bearer token.

Conceptually:

```text
Incoming Request
        ↓
Read Authorization Header
        ↓
Authorization Header Present?
        ↓
Check for "Bearer "
        ↓
Extract JWT
```

If the request contains:

```text
Authorization: Bearer <JWT>
```

the filter can extract the token by removing the `Bearer ` prefix.

Conceptually:

```java
String jwt = authorizationHeader.substring(7);
```

The value `7` represents the length of the string:

```text
Bearer 
```

including the trailing space.

The extracted JWT can then be passed to the validation logic.

---

## JWT Validation Flow

The custom JWT filter is responsible for handling JWT authentication for subsequent requests.

The simplified flow is:

```text
Incoming Request
    ↓
JWTAuthenticationFilter
    ↓
Read Authorization Header
    ↓
Check Bearer Token
    ↓
Extract JWT
    ↓
Validate JWT
    ↓
Extract User Information
    ↓
Create Authentication
    ↓
Store Authentication in SecurityContext
    ↓
Continue Filter Chain
```

The JWT validation process is separate from the initial login authentication.

During login, the application verifies the user's username and password and then generates a JWT.

During subsequent requests, the application receives the JWT and verifies whether it is valid.

The two stages can therefore be remembered as:

```text
Login

Username + Password
        ↓
AuthenticationManager
        ↓
Credentials Verified
        ↓
Generate JWT
        ↓
Return JWT
```

and:

```text
Subsequent Request

JWT
        ↓
JWTAuthenticationFilter
        ↓
Validate JWT
        ↓
Authenticate User
        ↓
SecurityContext
```

The exact JWT validation and token-parsing implementation will be covered next.