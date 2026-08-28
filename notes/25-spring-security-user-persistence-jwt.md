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

---

### UserDetailsService

The database-backed `UserDetailsService` can be injected into the security configuration.

```java
@Autowired
UserDetailsService userDetailsService;
```

The `UserDetailsService` is used to load user details when Spring Security needs to authenticate or restore the authenticated user.

---

### Security Configuration

The registration endpoint can be made publicly accessible using `permitAll()`.

Remember Me can then be configured with the `UserDetailsService`.

```java
http
    .antMatchers("/user/register").permitAll()
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

This configuration:

- Allows everyone to access `/user/register`.
- Enables Remember Me functionality.
- Provides the `UserDetailsService` required to load user details.
- Configures a custom login page at `/login`.
- Allows everyone to access the login page.
- Deletes the `remember-me` cookie during logout.

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

Spring Security can use the Remember Me information to restore authentication.

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

It is a compact token format used to securely transmit information between two parties.

A JWT can be created and signed by one application. Another application can verify that the token is authentic and has not been altered by validating its signature.

---

### JWT Structure

A JWT consists of three parts:

**Header**

↓

**Payload**

↓

**Signature**

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

## JWT Authentication Flow

JWT authentication can still use Spring Security's existing authentication components.

The general authentication flow remains similar:

Authentication Request

↓

Authentication Filter

↓

Authentication Manager

↓

Authentication Process

↓

Authenticated User

The main difference is that JWT authentication uses a custom JWT authentication filter instead of relying only on the default authentication filter.

Conceptually:

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

Request

↓

JWTAuthenticationFilter

↓

`doFilterInternal()`

↓

JWT Authentication Logic

↓

Continue Filter Chain

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

The method provides access to:

HttpServletRequest → the incoming request
HttpServletResponse → the outgoing response
FilterChain → the remaining filters in the Spring Security filter chain

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