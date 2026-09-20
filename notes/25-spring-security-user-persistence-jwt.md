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

### JPA Relationship Cheat Sheet

These annotations describe **how entities are related**, while parameters such as `cascade` and `fetch` describe **how the relationship behaves**.

| Concept | Meaning |
|---|---|
| `@OneToOne` | One entity ↔ one entity |
| `@OneToMany` | One entity → many entities |
| `@ManyToOne` | Many entities → one entity |
| `@ManyToMany` | Many entities ↔ many entities |
| `mappedBy` | The other entity owns the relationship |
| `@JoinColumn` | Defines the foreign-key column |
| `@JoinTable` | Uses an intermediate join table |
| `joinColumns` | Foreign key for this entity in the join table |
| `inverseJoinColumns` | Foreign key for the other entity |
| `cascade` | Defines which operations propagate |
| `fetch` | Defines when related data is loaded |

### How to Read `mappedBy`

```java
@OneToMany(mappedBy = "user")
private List<Exercise> exerciseList;
```

Here:

- `@OneToMany` → one `User` has many `Exercise` records.
- `mappedBy = "user"` → the `Exercise` entity owns the relationship.
- `"user"` is the **Java field name** inside `Exercise`, not the database column name.

The other side would typically contain:

```java
@ManyToOne
private User user;
```

### How to Remember `@JoinTable`

For:

```java
@ManyToMany
@JoinTable(
    name = "user_role",
    joinColumns = @JoinColumn(name = "user"),
    inverseJoinColumns = @JoinColumn(name = "role")
)
private Set<Role> roles;
```

Think:

```text
User
 ↓
user_role
 ↓
Role
```

- `joinColumns` → this entity (`User`)
- `inverseJoinColumns` → other entity (`Role`)

### FitFusion Relationship Map

The FitFusion application uses:

```text
User 1 ───────── * Exercise
User 1 ───────── * Diet
User * ───────── * Role
```

Typical mapping:

```java
// User
@OneToMany(mappedBy = "user")
private List<Exercise> exerciseList;

@OneToMany(mappedBy = "user")
private List<Diet> diets;

@ManyToMany
private Set<Role> roles;
```

```java
// Exercise
@ManyToOne
private User user;
```

```java
// Diet
@ManyToOne
private User user;
```

```text
@OneToMany / @ManyToOne
        ↓
      "How many?"

mappedBy
        ↓
"Who owns the relationship?"

@JoinColumn / @JoinTable
        ↓
"How is the relationship stored?"
```

> **Important:** `cascade = CascadeType.ALL` and `fetch = FetchType.EAGER` are configuration choices. They are **not required simply because a relationship is `@OneToMany` or `@ManyToMany`**.

---

## Custom UserDetailsService

For database-backed authentication, Spring Security needs a way to load a user's stored information.

This is done through the `UserDetailsService` interface.

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


```java
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }
}
```

### FitFusion Implementation

In FitFusion, the user's **email acts as the username**.

```java
@Override
public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {

    return userRepository.findByEmail(username)
            .orElseThrow(() ->
                    new UserNotFoundException("User not found"));
}
```

The important connection is:

```text
Login username
      ↓
UserDetailsService.loadUserByUsername()
      ↓
UserRepository.findByEmail()
      ↓
User entity from database
      ↓
UserDetails
```

### Key Idea

`UserDetailsService` does **not** authenticate the password itself.

Its main responsibility is to **load the user's stored security information**.

Spring Security can then use that information to perform authentication.

---

## Implementing UserDetails

The application's persistent `User` entity can implement Spring Security's `UserDetails` interface.

```java
@Entity
public class User implements UserDetails {

    private String email;
    private String password;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // Other UserDetails methods...
}
```

This allows the application's database entity to provide the information Spring Security needs.

### Important Mapping

In FitFusion:

```text
Database User.email
        ↓
User.getUsername()
        ↓
Spring Security username
```

Therefore, even though the application does not have a separate `username` field, Spring Security can still treat the user's email as the username.

### UserDetails Methods

The important methods are:

| Method | Purpose |
|---|---|
| `getUsername()` | Returns the identity used for authentication |
| `getPassword()` | Returns the stored encoded password |
| `getAuthorities()` | Returns the user's roles/permissions |
| `isAccountNonExpired()` | Whether the account is not expired |
| `isAccountNonLocked()` | Whether the account is not locked |
| `isCredentialsNonExpired()` | Whether credentials are still valid |
| `isEnabled()` | Whether the account is enabled |

A common simple implementation returns `true` for the account-status methods when the application does not implement those restrictions.

---

## Granted Authorities

Spring Security uses `GrantedAuthority` objects to represent what an authenticated user is allowed to do.

In FitFusion, roles are stored in the database and converted into `SimpleGrantedAuthority` objects.

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

The flow is:

```text
Roles stored in database
        ↓
User.getAuthorities()
        ↓
Stream through roles
        ↓
Convert each role to SimpleGrantedAuthority
        ↓
Return Collection<GrantedAuthority>
```

### Role vs Authority

A useful distinction:

- **Role** → application-level concept such as `ADMIN`, `CUSTOMER`, `TRAINER`
- **GrantedAuthority** → Spring Security representation of an allowed role/permission

For example:

```java
new SimpleGrantedAuthority("ADMIN")
```

creates an authority whose value is `"ADMIN"`.

### `hasRole()` vs `hasAuthority()`

This distinction is important in coding problems.

```java
@PreAuthorize("hasRole('ADMIN')")
```

typically checks for:

```text
ROLE_ADMIN
```

whereas:

```java
@PreAuthorize("hasAuthority('ADMIN')")
```

checks for:

```text
ADMIN
```

Therefore, the value stored in `GrantedAuthority` must match the expression being used.

For example, if the application stores:

```java
new SimpleGrantedAuthority("ROLE_ADMIN")
```

then:

```java
hasRole("ADMIN")
```

is appropriate.

If the application stores:

```java
new SimpleGrantedAuthority("ADMIN")
```

then:

```java
hasAuthority("ADMIN")
```

matches directly.

### FitFusion Check

When working on the FitFusion implementation, always check:

```text
Role.roleName
      ↓
SimpleGrantedAuthority(...)
      ↓
hasRole(...) / hasAuthority(...)
```

A role-prefix mismatch can cause an authenticated user to receive `403 Forbidden` even though the login itself succeeds.

---

## AuthenticationManager Configuration

For simple declarative authentication such as basic authentication or form login, Spring Security can often configure the authentication flow automatically.

FitFusion is different because the login API explicitly calls:

```java
manager.authenticate(authenticationToken);
```

Therefore, an `AuthenticationManager` is required.

```java
@Bean
public AuthenticationManager authenticationManager(
        AuthenticationConfiguration builder) throws Exception {

    return builder.getAuthenticationManager();
}
```

### FitFusion Authentication Flow

The important flow is:

```text
POST /auth/login
        ↓
AuthController
        ↓
AuthService.login()
        ↓
UsernamePasswordAuthenticationToken
        ↓
AuthenticationManager.authenticate()
        ↓
UserDetailsService
        ↓
UserRepository
        ↓
User from database
        ↓
PasswordEncoder verifies password
        ↓
Authentication succeeds
```

The important point is that `AuthenticationManager` coordinates the authentication process.

It does not mean that `AuthenticationManager` itself directly queries the database.

### FitFusion `AuthService`

The actual login service follows two important steps:

```java
public JwtResponse login(JwtRequest jwtRequest) {

    this.doAuthenticate(
            jwtRequest.getUsername(),
            jwtRequest.getPassword()
    );

    UserDetails userDetails =
            userDetailService.loadUserByUsername(
                    jwtRequest.getUsername()
            );

    String token =
            jwtHelper.generateToken(userDetails);

    return JwtResponse.builder()
            .jwtToken(token)
            .build();
}
```

The authentication step is:

```java
UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
                username,
                password
        );

manager.authenticate(authenticationToken);
```

After successful authentication, the user details are loaded again and used to generate the JWT.

### Why Is AuthenticationManager Important Here?

Because the application is performing **programmatic authentication**.

The login API receives:

```text
username + password
```

and explicitly asks Spring Security to authenticate them:

```java
manager.authenticate(authenticationToken);
```

After successful authentication:

```text
Authenticated user
        ↓
UserDetails
        ↓
JWT generation
        ↓
JwtResponse
```

### PasswordEncoder

The authentication process also relies on a `PasswordEncoder`.

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

During registration:

```text
Plain password
      ↓
BCrypt encoding
      ↓
Encoded password stored in database
```

During login:

```text
Entered password
      ↓
AuthenticationManager
      ↓
PasswordEncoder verification
      ↓
Stored encoded password
      ↓
Authentication success/failure
```

The application should **never compare the plain-text password directly with the encoded database value**.

---

## Database-Backed Authentication: Complete Mental Model

The complete relationship between the main Spring Security components can be remembered as:

```text
                    DATABASE
                       │
                       ↓
                 UserRepository
                       │
                       ↓
             CustomUserDetailsService
                       │
                       ↓
                  UserDetails
                       │
          ┌────────────┴────────────┐
          ↓                         ↓
     Password                  Authorities
          │                         │
          ↓                         ↓
  PasswordEncoder          GrantedAuthority
          │                         │
          └────────────┬────────────┘
                       ↓
              AuthenticationManager
                       │
                       ↓
               Authentication
                       │
                       ↓
              SecurityContext
                       │
                       ↓
              Authorization rules
```

### Component Responsibility Map

| Component | Main Responsibility |
|---|---|
| `UserRepository` | Retrieves persisted users |
| `UserDetailsService` | Loads user security information |
| `User implements UserDetails` | Adapts application user data to Spring Security |
| `GrantedAuthority` | Represents roles/permissions |
| `PasswordEncoder` | Encodes and verifies passwords |
| `AuthenticationManager` | Coordinates authentication |
| `Authentication` | Represents the authentication result |
| `SecurityContext` | Holds the current authenticated user |
| `@PreAuthorize` | Applies method-level authorization |

### Key Idea

Think of the responsibilities in this order:

```text
Load user
   ↓
Verify credentials
   ↓
Create authenticated identity
   ↓
Attach authorities
   ↓
Apply authorization rules
```

**Authentication answers:**  
> "Who is this user?"

**Authorization answers:**  
> "What is this authenticated user allowed to do?"

---

## Method-Level Security

Spring Security can also apply authorization rules directly at the method level.

Method-level security allows specific methods in a controller or service to be protected based on roles or other security conditions.

The `@PreAuthorize` annotation can be used to define an authorization rule that must be satisfied before a method is executed.

For example:

```java
@RestController
@RequestMapping("/basePath")
public class Controller {

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void createEntity(
            @RequestBody SampleEntity sampleEntity
    ) {
        // ...
    }
}
```

Here:

- `@PreAuthorize` defines an authorization rule that is checked before the method executes.
- `hasRole('ADMIN')` requires the authenticated user to have the `ADMIN` role.
- If the user does not have the required role, access to the method is denied.

Method-level security must be enabled in the Spring Security configuration.

In the configuration used in this section:

```java
@EnableGlobalMethodSecurity(prePostEnabled = true)
```

The `prePostEnabled = true` setting enables pre/post authorization annotations such as `@PreAuthorize`.

The overall flow can be understood as:

```text
Request
    ↓
Authentication
    ↓
Authenticated User
    ↓
@PreAuthorize checks authorization
    ↓
Required role/condition satisfied?
    ↓
Yes → Method executes
No  → Access denied
```

**Key idea:**

URL-level authorization controls access based on request paths, while method-level security allows authorization rules to be applied directly to specific methods.

---

## URL-Level Role Authorization

Spring Security can apply authorization rules directly to URL patterns.

The `antMatchers()` method can be used to match requests to specific URL patterns, and `hasRole()` can restrict those requests to users with a particular role.

For example:

```java
http
    .authorizeHttpRequests()
        .antMatchers("/pathForAdmin/**")
        .hasRole("ADMIN")
        .antMatchers("/pathForUser/**")
        .hasRole("USER")
        .anyRequest()
        .authenticated();
```

Here:

- `antMatchers("/pathForAdmin/**")` matches requests under the specified URL pattern.
- `hasRole("ADMIN")` requires the authenticated user to have the `ADMIN` role.
- `antMatchers("/pathForUser/**")` matches requests under the user URL pattern.
- `hasRole("USER")` requires the authenticated user to have the `USER` role.
- `anyRequest().authenticated()` requires authentication for requests that do not have a more specific authorization rule.

The authorization flow can be understood as:

```text
Incoming Request
        ↓
URL Pattern Matching
        ↓
Required Role
        ↓
User Has Required Role?
        ↓
Yes → Request Allowed
No  → Access Denied
```

This provides **URL-level authorization**, where access is controlled based on the requested endpoint.

URL-level authorization and method-level authorization can both be used in the same application:

```text
URL-Level Authorization
        ↓
Controls access based on endpoint

Method-Level Authorization
        ↓
Controls access based on specific method
```

The exact configuration syntax depends on the Spring Security version used by the application. The example above follows the configuration style used in the CodingNinjas material and application implementation.

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

### Remember Me Security Considerations

Remember Me keeps authentication available for longer than the normal session, so the persistent authentication mechanism must be protected carefully.

Important considerations include:

- **Secure cookies** → The Remember Me cookie should use appropriate cookie security settings such as `Secure` and `HttpOnly`.
- **Strong tokens** → The persistent authentication token should be long and difficult to guess.
- **Token rotation and expiration** → Tokens should have an appropriate lifetime and may be rotated or refreshed to reduce the risk of token misuse.
- **User control** → Users should be able to choose whether Remember Me is enabled and should be able to disable it or log out.
- **Session management** → The application should securely handle both normal sessions and Remember Me authentication.
- **Account protection** → Additional protections such as account lockout policies can reduce the impact of unauthorized access to a user's device.

### Mental Model

```text
Remember Me
      ↓
Persistent authentication token
      ↓
Protect the token
      ↓
Secure + HttpOnly cookie
      ↓
Strong token
      ↓
Expiration / rotation
      ↓
User can disable / logout
```

### Interview Takeaway

> Remember Me improves convenience by allowing authentication to survive beyond the normal session, but the persistent authentication token must be protected with secure cookie settings, strong tokens, appropriate expiration/rotation, and proper session management.

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

In a JWT-based application, login is responsible for:

1. Receiving the username/email and password.
2. Authenticating the credentials.
3. Loading the authenticated user's details.
4. Generating a JWT.
5. Returning the JWT to the client.

In FitFusion, the login endpoint is exposed through `AuthController`.

### FitFusion `AuthController`

```java
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        return new ResponseEntity<>(
            authService.login(jwtRequest),
            HttpStatus.OK
        );
    }
}
```

The controller itself does not authenticate the user or generate the JWT.

It delegates the work to `AuthService`.

---

## Generating and Returning the JWT

### FitFusion `AuthService`

```java
@Service
public class AuthService {

    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtAuthenticationHelper jwtHelper;

    @Autowired
    UserDetailsService userDetailService;

    public JwtResponse login(JwtRequest jwtRequest) {

        this.doAuthenticate(
            jwtRequest.getUsername(),
            jwtRequest.getPassword()
        );

        UserDetails userdetails =
            userDetailService.loadUserByUsername(
                jwtRequest.getUsername()
            );

        String token = jwtHelper.generateToken(userdetails);

        JwtResponse response = JwtResponse.builder()
            .jwtToken(token)
            .build();

        return response;
    }

    private void doAuthenticate(String username, String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(
                username,
                password
            );

        try {
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(
                "Invalid username or password"
            );
        }
    }
}
```

### What happens inside `login()`?

The FitFusion login process has two important stages.

#### Stage 1 — Authenticate the credentials

```java
this.doAuthenticate(
    jwtRequest.getUsername(),
    jwtRequest.getPassword()
);
```

`doAuthenticate()` creates a `UsernamePasswordAuthenticationToken` containing the credentials:

```java
UsernamePasswordAuthenticationToken authenticationToken =
    new UsernamePasswordAuthenticationToken(username, password);
```

It then passes the token to:

```java
manager.authenticate(authenticationToken);
```

The `AuthenticationManager` coordinates authentication.

For FitFusion, the authentication process ultimately uses:

- `UserDetailsService` to load the user
- `UserRepository` to find the user by email
- `PasswordEncoder` to verify the submitted password against the stored encoded password
- the user's authorities/roles as part of the authenticated identity

If authentication fails, `BadCredentialsException` is thrown.

---

#### Stage 2 — Load `UserDetails` and generate the JWT

After successful authentication:

```java
UserDetails userdetails =
    userDetailService.loadUserByUsername(
        jwtRequest.getUsername()
    );
```

The user's details are loaded again and passed to:

```java
String token = jwtHelper.generateToken(userdetails);
```

The generated JWT is then placed inside `JwtResponse`:

```java
JwtResponse response = JwtResponse.builder()
    .jwtToken(token)
    .build();
```

Finally, the controller returns the response to the client.

---

## FitFusion JWT Login Flow

```text
Client
  |
  | POST /auth/login
  | username + password
  ↓
AuthController
  |
  ↓
AuthService.login()
  |
  ↓
AuthenticationManager.authenticate()
  |
  ↓
UserDetailsService
  |
  ↓
UserRepository.findByEmail()
  |
  ↓
PasswordEncoder verifies password
  |
  ↓
Authentication succeeds
  |
  ↓
UserDetails loaded
  |
  ↓
JwtAuthenticationHelper.generateToken()
  |
  ↓
JwtResponse
  |
  ↓
Client receives JWT
```

### Important distinction

`AuthenticationManager` is used here because FitFusion performs **programmatic authentication**:

```java
manager.authenticate(authenticationToken);
```

After authentication succeeds, the application generates a JWT that the client can use for subsequent protected requests.

The JWT itself does not contain the user's password.

---

## JWT Token Generation

FitFusion uses `JwtAuthenticationHelper` to create the token.

```java
@Component
public class JwtAuthenticationHelper {

    private String secret =
        "tanamanamanamajsddldldldldldldmmcfhvjhbnfnmsdbfmsbvnmbfvnmbvmbjllllddmmmxxxnnneeelllsssnnnsnsnsnsnsnsnsnsnhisisaverylongsecretkeyforjwtgenerationandvalidation12345";

    private static final long JWT_TOKEN_VALIDITY = 60 * 60;

    public String generateToken(UserDetails userdetails) {

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userdetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(
                new Date(
                    System.currentTimeMillis()
                    + JWT_TOKEN_VALIDITY * 1000
                )
            )
            .signWith(
                new SecretKeySpec(
                    secret.getBytes(),
                    SignatureAlgorithm.HS512.getJcaName()
                ),
                SignatureAlgorithm.HS512
            )
            .compact();
    }
}
```

### Important parts of token generation

#### Subject

```java
.setSubject(userdetails.getUsername())
```

The authenticated user's username is stored as the JWT subject.

In FitFusion:

```text
username → email
```

because `User.getUsername()` returns the user's email.

#### Issued-at time

```java
.setIssuedAt(new Date(System.currentTimeMillis()))
```

Records when the token was issued.

#### Expiration

```java
.setExpiration(
    new Date(
        System.currentTimeMillis()
        + JWT_TOKEN_VALIDITY * 1000
    )
)
```

FitFusion sets:

```java
private static final long JWT_TOKEN_VALIDITY = 60 * 60;
```

This represents one hour in seconds.

#### Signature

```java
.signWith(
    new SecretKeySpec(
        secret.getBytes(),
        SignatureAlgorithm.HS512.getJcaName()
    ),
    SignatureAlgorithm.HS512
)
```

The token is signed so that the server can later verify that the token was created using the expected signing secret.

### Important security note

The secret shown above is the **course/FitFusion implementation**.

In a production application, secrets should not be hardcoded in source code. They should be managed through appropriate configuration/secrets management.

Also, JWT is **signed, not encrypted**. Its payload should therefore not be treated as a place for sensitive information such as passwords.

### JJWT version note

The exact JJWT API is version-sensitive.

The FitFusion/course implementation uses APIs such as:

```java
Jwts.parserBuilder()
```

and:

```java
signWith(key, algorithm)
```

When implementing this in another project, verify the API against the JJWT version declared in `pom.xml`.

---

## JWT Login Mental Model

For coding problems, remember the sequence:

```text
Login Request
    ↓
Authenticate credentials
    ↓
AuthenticationManager
    ↓
UserDetailsService
    ↓
UserRepository
    ↓
PasswordEncoder
    ↓
Authentication successful
    ↓
Load UserDetails
    ↓
Generate JWT
    ↓
Return JwtResponse
```

The key separation is:

- **AuthenticationManager** → verifies the login credentials
- **UserDetailsService** → loads user information
- **PasswordEncoder** → verifies the encoded password
- **JwtAuthenticationHelper** → creates the JWT
- **JwtResponse** → sends the token back to the client

The next stage is using that JWT on subsequent requests. The JWT filter responsible for extracting, validating, and placing the authenticated user into the `SecurityContext` is covered separately.
  
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

The custom JWT filter handles authentication for subsequent requests.

The core flow is:

```text
Incoming Request
    ↓
JWTAuthenticationFilter
    ↓
Read Authorization Header
    ↓
Check "Bearer "
    ↓
Extract JWT
    ↓
Extract Username
    ↓
Load UserDetails
    ↓
Check Token Expiration
    ↓
Create Authentication
    ↓
Store in SecurityContext
    ↓
Continue Filter Chain
```

### FitFusion `JwtAuthenticationFilter`

FitFusion implements the JWT filter using `OncePerRequestFilter`.

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtAuthenticationHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        if (requestHeader != null &&
            requestHeader.startsWith("Bearer ")) {

            token = requestHeader.substring(7);

            username = jwtHelper.getUsernameFromToken(token);

            if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailService.loadUserByUsername(username);

                if (!jwtHelper.isTokenExpired(token)) {

                    UsernamePasswordAuthenticationToken
                            usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    token,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder.getContext()
                            .setAuthentication(
                                    usernamePasswordAuthenticationToken
                            );
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

### Important Steps

#### 1. Read the Authorization Header

```java
String requestHeader = request.getHeader("Authorization");
```

The filter reads the JWT from the `Authorization` header.

Expected format:

```text
Authorization: Bearer <JWT>
```

#### 2. Check the Bearer Prefix

```java
requestHeader.startsWith("Bearer ")
```

This confirms that the request contains a Bearer token.

#### 3. Extract the JWT

```java
token = requestHeader.substring(7);
```

`Bearer ` contains 7 characters, so `substring(7)` removes the prefix and leaves the JWT.

#### 4. Extract the Username

```java
username = jwtHelper.getUsernameFromToken(token);
```

The JWT helper extracts the username from the token's subject.

In FitFusion:

```text
JWT subject
    ↓
User email
```

#### 5. Check the Security Context

```java
SecurityContextHolder.getContext().getAuthentication() == null
```

The filter checks whether authentication has already been established for the current request.

If authentication is already present, the filter does not create another authentication object.

#### 6. Load UserDetails

```java
UserDetails userDetails =
        userDetailService.loadUserByUsername(username);
```

The username extracted from the JWT is used to load the user from the database.

The flow is:

```text
JWT
 ↓
Username / Email
 ↓
UserDetailsService
 ↓
UserRepository
 ↓
UserDetails
```

#### 7. Check Token Expiration

```java
if (!jwtHelper.isTokenExpired(token))
```

The token must not be expired before authentication is established.

Conceptually:

```text
JWT
 ↓
Check expiration
 ↓
Valid → Continue
Expired → Do not authenticate
```

#### 8. Create Authentication

```java
new UsernamePasswordAuthenticationToken(
        token,
        null,
        userDetails.getAuthorities()
);
```

This creates an authenticated Spring Security `Authentication` object containing the user's authorities.

The important point is that the password is not being authenticated again here.

The JWT has already been issued after successful login. The filter validates the token and establishes the authenticated identity for the current request.

#### 9. Store Authentication in SecurityContext

```java
SecurityContextHolder.getContext()
        .setAuthentication(
                usernamePasswordAuthenticationToken
        );
```

This places the authenticated user into Spring Security's `SecurityContext`.

After this point, authorization mechanisms can use the authenticated identity and authorities.

```text
JWT
 ↓
Validated User
 ↓
Authentication
 ↓
SecurityContext
 ↓
Authorization
```

#### 10. Continue the Filter Chain

```java
filterChain.doFilter(request, response);
```

The request continues to the remaining filters and eventually the controller.

This is important because the JWT filter is only one part of the Spring Security filter chain.

---

## JWT Authentication Request Flow

The complete FitFusion flow can be remembered as:

```text
Client
   ↓
Authorization: Bearer <JWT>
   ↓
JwtAuthenticationFilter
   ↓
Extract JWT
   ↓
Extract username
   ↓
Load UserDetails
   ↓
Check token expiration
   ↓
Create Authentication
   ↓
SecurityContext
   ↓
Authorization
   ↓
Controller
```

### Login vs Subsequent Request

These two flows should not be confused.

**Login:**

```text
Username + Password
        ↓
AuthenticationManager
        ↓
Credentials verified
        ↓
Generate JWT
        ↓
Return JWT
```

**Subsequent request:**

```text
JWT
 ↓
JwtAuthenticationFilter
 ↓
Validate JWT
 ↓
Load UserDetails
 ↓
Create Authentication
 ↓
SecurityContext
 ↓
Authorization
 ↓
Controller
```

### Key Idea

The JWT filter does **not** generate the JWT.

Its job is to:

- Read the JWT from the request.
- Extract the user's identity.
- Validate the token.
- Load the user's authorities.
- Create an `Authentication` object.
- Store it in the `SecurityContext`.
- Continue the request through the filter chain.

The login service generates the JWT; the JWT filter uses that JWT to establish authentication for later requests.

---

## JWT Propagation with RestTemplate

When one backend service calls another protected service, the JWT must be forwarded in the `Authorization` header.

### Sending JWT to Another Service

```java
HttpHeaders headers = new HttpHeaders();
headers.setBearerAuth(jwt);

HttpEntity<?> entity = new HttpEntity<>(headers);

ResponseEntity<Rating[]> response =
    restTemplate.exchange(
        ratingServiceUrl,
        HttpMethod.GET,
        entity,
        Rating[].class
    );
```

The important part is:

```text
Authorization: Bearer <JWT>
```

### Service-to-Service Flow

```text
Client
  ↓
Login / Authentication
  ↓
JWT issued
  ↓
Service A
  ↓
RestTemplate
  ↓
Authorization: Bearer <JWT>
  ↓
Service B
  ↓
JWT Authentication Filter
  ↓
SecurityContext
  ↓
Protected Controller
```

### Mental Model

The JWT is not only used between the client and the backend.

It can also be propagated when one authenticated service calls another protected service:

```text
JWT received
    ↓
Service A extracts/keeps JWT
    ↓
Service A adds JWT to Authorization header
    ↓
Service B receives JWT
    ↓
JWT filter validates token
    ↓
SecurityContext gets authenticated user
    ↓
Protected endpoint executes
```

### Interview Takeaway

> In a JWT-based service-to-service call, the calling service forwards the JWT using the `Authorization: Bearer <JWT>` header so the receiving service can authenticate the request through its JWT security filter.

**Note:** `RestTemplate` is the client used in the course material. The general RestTemplate concepts are already covered in Note 22, so this section only records the **JWT propagation/security integration**.