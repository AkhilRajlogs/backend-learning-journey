# Spring Security — Password Encoding

## 1. Why Password Encoding Is Important

Passwords should **never be stored as plain text** in a database.

Instead, applications store a one-way password hash and use it during authentication to verify the supplied password.

> In Spring Security, `PasswordEncoder` is commonly used for password hashing. The term "encoding" is used by the API, but this is not reversible encryption.

### Basic flow

```text
User enters password
        ↓
PasswordEncoder
        ↓
One-way password hash
        ↓
Store encoded password in database
```

During login:

```text
Raw password
     ↓
PasswordEncoder.matches(...)
     ↓
Compare with stored encoded password
     ↓
Authentication succeeds/fails
```

Use `matches()` rather than manually hashing the password and comparing strings because algorithms such as BCrypt use a salt as part of the stored encoded value.

---

## 2. Hashing

Hashing converts input into a fixed-length value using a one-way function.

```text
Plain Password
      ↓
    Hash
      ↓
Encoded Password
```

A secure password hash should make it computationally difficult to recover the original password.

### Important properties

- One-way — the original password should not be recoverable from the hash.
- Password hashing should be intentionally slow.
- A unique salt should be used for each password.
- Modern password hashing algorithms should be resistant to brute-force and hardware-accelerated attacks.

### Important distinction

General-purpose hashes such as:

- MD5
- SHA-1
- SHA-256
- SHA-512

are useful for many cryptographic purposes, but **fast hashes are not suitable for storing passwords**.

Password storage should use a password hashing/KDF algorithm designed to be slow and configurable, such as:

- BCrypt
- PBKDF2
- Argon2
- SCrypt

---

## 3. Salting

A **salt** is a random value combined with a password before hashing.

```text
Password + Random Salt
          ↓
        Hash
          ↓
     Stored Value
```

The salt:

- should be unique for each password
- does not need to be secret
- makes precomputed attacks such as rainbow-table attacks much harder
- is normally stored together with the password hash

Modern password encoders such as BCrypt generate and manage the salt automatically.

---

## 4. BCrypt

`BCryptPasswordEncoder` is a common password encoder in Spring Security.

BCrypt is designed specifically for password hashing and supports an adjustable computational cost.

### Encoding a password

```java
BCryptPasswordEncoder passwordEncoder =
        new BCryptPasswordEncoder();

String encodedPassword =
        passwordEncoder.encode(plainPassword);
```

The encoded password is what should be stored in the database.

### Verifying a password

```java
boolean matches =
        passwordEncoder.matches(plainPassword, encodedPassword);
```

Do **not** do:

```java
passwordEncoder.encode(plainPassword).equals(encodedPassword);
```

because a new salt can produce a different encoded value even when the original password is the same.

---

## 5. BCrypt in Spring Security

A `PasswordEncoder` bean can be registered and injected wherever password encoding is required.

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

Then during user registration:

```java
user.setPassword(passwordEncoder.encode(user.getPassword()));
userRepository.save(user);
```

During authentication, Spring Security uses the configured `PasswordEncoder` to verify the supplied password against the stored encoded password.

### Typical registration flow

```text
User submits registration
        ↓
Receive plain password
        ↓
PasswordEncoder.encode(...)
        ↓
Store encoded password
        ↓
Database
```

### Typical login flow

```text
User submits username + password
        ↓
Spring Security
        ↓
Load stored user
        ↓
PasswordEncoder.matches(...)
        ↓
Authentication succeeds/fails
```

---

## 6. PBKDF2

**PBKDF2 (Password-Based Key Derivation Function 2)** is a password-based key derivation function.

It repeatedly applies a cryptographic operation to make password guessing more expensive.

Important characteristics:

- Uses a salt.
- Uses configurable iterations.
- Makes brute-force attacks more expensive.
- Commonly used when compatibility or standards requirements call for PBKDF2.

### Course example

```java
String secret = "MySecret";
int iterations = 500;
int hashWidth = 512;

Pbkdf2PasswordEncoder passwordEncoder =
        new Pbkdf2PasswordEncoder(secret, iterations, hashWidth);

passwordEncoder.setEncodeHashAsBase64(true);

String encodedPassword =
        passwordEncoder.encode(plainPassword);
```

> **Note:** Spring Security constructor signatures and configuration APIs can vary between versions. Treat the course code as version-specific implementation material.

---

## 7. Argon2

**Argon2** is a modern password-hashing algorithm designed to make brute-force attacks expensive, especially with specialized hardware.

It is **memory-hard**, meaning it intentionally requires significant memory in addition to computational work.

Important characteristics:

- Uses a salt.
- Configurable memory usage.
- Configurable iterations.
- Configurable parallelism.
- Designed to resist GPU/ASIC-based attacks.

### Course example

```java
int saltLength = 32;
int hashLength = 8;
int parallelism = 2;
int memory = 512;
int iterations = 5;

Argon2PasswordEncoder passwordEncoder =
        new Argon2PasswordEncoder(
                saltLength,
                hashLength,
                parallelism,
                memory,
                iterations
        );

String encodedPassword =
        passwordEncoder.encode(plainPassword);
```

> **Note:** The exact constructor and supported parameters depend on the Spring Security version used by the project/course.

---

## 8. SCrypt

**SCrypt** is another memory-hard password-based key derivation function.

It is designed to increase the cost of brute-force attacks by requiring both computational resources and memory.

Important characteristics:

- Uses a salt.
- Memory-hard.
- Computationally expensive.
- Designed to make large-scale hardware attacks more difficult.

### Course example

```java
int cpuCost = (int) Math.pow(2, 14);
int memoryCost = 8;
int parallelization = 1;
int keyLength = 32;
int saltLength = 64;

SCryptPasswordEncoder passwordEncoder =
        new SCryptPasswordEncoder(
                cpuCost,
                memoryCost,
                parallelization,
                keyLength,
                saltLength
        );

String encodedPassword =
        passwordEncoder.encode(plainPassword);
```

> **Note:** Constructor signatures and supported parameters can vary between Spring Security versions.

---

## 9. Password Encoding Algorithms — Quick Comparison

| Algorithm | Main characteristic | Salt | Configurable Cost |
|---|---|---:|---:|
| BCrypt | Adaptive password hashing | Yes | Yes |
| PBKDF2 | Repeated key derivation | Yes | Yes |
| Argon2 | Memory-hard password hashing | Yes | Yes |
| SCrypt | Memory-hard key derivation | Yes | Yes |
| MD5 | Fast general-purpose hash | No built-in password protection | No |
| SHA-256 | Fast general-purpose hash | No built-in password protection | No |

For password storage, prefer a password-specific, adaptive algorithm rather than a fast general-purpose hash.

---

## 10. Key Takeaways

- Never store plain-text passwords.
- Use a password-specific `PasswordEncoder`.
- BCrypt automatically handles salting.
- Store the encoded password, not the raw password.
- Use `passwordEncoder.matches()` when verifying passwords.
- Salts do not need to be secret.
- Fast hashes such as MD5 and SHA-1 should not be used for password storage.
- PBKDF2, BCrypt, Argon2, and SCrypt are designed to make password attacks more expensive.
- Password hashing is **not encryption** — it is intended to be one-way.
- Password hashing parameters should be chosen according to current security requirements and updated over time.

### Spring Security mental model

```text
Registration
    ↓
Raw Password
    ↓
PasswordEncoder.encode()
    ↓
Encoded Password
    ↓
Database


Login
    ↓
Username + Raw Password
    ↓
UserDetailsService
    ↓
Stored Encoded Password
    ↓
PasswordEncoder.matches()
    ↓
Authentication
```

---

# CSRF Protection

## 1. What is CSRF?

**CSRF (Cross-Site Request Forgery)** is an attack where an attacker tricks an already authenticated user into sending an unwanted request to an application.

The key idea is:

> The victim is already authenticated, so the browser automatically sends the user's session credentials with the malicious request.

### Example

Suppose a user is logged into a banking application.

```text
Victim logs into bank
        ↓
Authenticated session is created
        ↓
Victim visits malicious website
        ↓
Malicious website sends request to bank
        ↓
Browser automatically includes session cookie
        ↓
Bank sees an authenticated request
        ↓
Unwanted action may be performed
```

The attacker does not necessarily need to know the user's password or session ID.

---

## 2. How a CSRF Attack Works

A typical CSRF attack requires:

1. The victim is authenticated with the target application.
2. The victim's browser has valid authentication credentials, such as a session cookie.
3. The victim is tricked into visiting or interacting with an attacker-controlled page.
4. The attacker's page causes a request to be sent to the target application.
5. The browser automatically includes the authentication cookie.
6. The target application may interpret the request as coming from the authenticated user.

### Example using an image request

An attacker could attempt to trigger a request through HTML:

```html
<img src="https://example.com/transfer?amount=1000&to=attacker">
```

If the target application incorrectly allows a state-changing operation through such a request and relies only on the user's session cookie for authentication, the request may be treated as authenticated.

---

## 3. CSRF with POST Requests

CSRF is not limited to GET requests.

An attacker can also attempt to submit a malicious form:

```html
<form action="https://example.com/transfer" method="POST">
    <input type="hidden" name="amount" value="1000">
    <input type="hidden" name="to" value="attacker">
</form>

<script>
    document.forms[0].submit();
</script>
```

The browser may include the victim's authentication cookie with the request.

Therefore, applications need a mechanism to verify that the request was intentionally generated by the application.

---

## 4. CSRF Tokens

A common defense is a **CSRF token**.

The application generates a token and associates it with the user's session or request context.

The token must be included with state-changing requests.

```text
User requests form
        ↓
Application generates CSRF token
        ↓
Token is included in form
        ↓
User submits form
        ↓
Application validates token
        ↓
Valid → Process request
Invalid → Reject request
```

An attacker generally cannot successfully forge the request because they do not have access to the valid CSRF token.

---

## 5. CSRF Protection in Spring Security

Spring Security provides CSRF protection for applications that use browser-based authentication.

For a typical form-based application, CSRF protection should remain enabled.

Example configuration:

```java
@Configuration
@EnableWebSecurity
public class HotelSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            UserDetailsService userDetailsService) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/register").permitAll()
                .anyRequest().authenticated()
            )
            .rememberMe(remember -> remember
                .userDetailsService(userDetailsService)
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .deleteCookies("remember-me")
            );

        return http.build();
    }
}
```

CSRF protection is enabled by default in Spring Security for the relevant application configurations.

If you explicitly configure it, the important concept is:

```java
http.csrf(csrf -> csrf.enable());
```

> Avoid disabling CSRF simply to make a form or request work. First determine why the CSRF token is missing or invalid.

---

## 6. CSRF Token in a Thymeleaf Form

When using Thymeleaf with Spring Security, the CSRF token can be included in the form.

Example:

```html
<form th:action="@{/login}" method="post">

    <input
        type="hidden"
        name="${_csrf.parameterName}"
        value="${_csrf.token}"
    />

    <input type="text" name="username">
    <input type="password" name="password">

    <button type="submit">Login</button>
</form>
```

The hidden field sends the CSRF token along with the request.

Spring Security then validates the token before allowing the request to proceed.

---

## 7. CSRF and REST APIs

CSRF protection depends on **how authentication credentials are transported and how the API is used**.

Browser applications using session cookies are particularly relevant because browsers automatically attach cookies to requests.

A stateless API that authenticates requests using a bearer token in the `Authorization` header has a different CSRF threat model because the browser does not automatically attach that header to cross-site requests.

For example:

```http
Authorization: Bearer <JWT>
```

However, this does **not** mean that every JWT-based application should blindly disable CSRF. The decision depends on the application's authentication mechanism and browser interaction model.

---

## 8. SameSite Cookies

`SameSite` cookie settings provide another layer of CSRF defense.

They control when browsers send cookies in cross-site requests.

Common modes include:

```text
Strict
Lax
None
```

### General idea

```text
Same-site request
      ↓
Cookie can be sent

Cross-site request
      ↓
SameSite policy determines
whether cookie is sent
```

SameSite cookies can reduce CSRF risk, but they should be considered part of a broader security strategy rather than the only defense.

---

## 9. Other CSRF Mitigations

Common defenses include:

### CSRF tokens

Require a secret request token that an attacker cannot easily obtain.

### SameSite cookies

Restrict when authentication cookies are sent in cross-site requests.

### Double-submit cookies

Use a cookie value together with a request value and verify that they match.

### Security headers

Security-related headers such as CSP and `X-Frame-Options` can provide additional browser-level protections, although they solve different problems and should not be treated as replacements for CSRF protection.

---

## 10. CSRF vs Authentication

A common interview mistake is to think:

> "The attacker does not know the user's password, so the request cannot be authenticated."

CSRF works differently.

The victim's browser already has valid authentication credentials.

```text
Attacker
   ↓
Tricks victim's browser
   ↓
Browser sends request
   ↓
Browser automatically sends session cookie
   ↓
Server sees authenticated user
```

Therefore:

**Authentication answers:**

> Who is making this request?

**CSRF protection helps answer:**

> Was this request intentionally generated by the application/user rather than forged from another site?

---

## 11. CSRF vs XSS

CSRF and XSS are different attacks.

| CSRF | XSS |
|---|---|
| Tricks a user's browser into sending an unwanted request | Injects malicious script into a page |
| Often abuses authenticated browser sessions | Executes attacker-controlled JavaScript |
| CSRF tokens and SameSite cookies help defend against it | Output encoding, sanitization and CSP help defend against it |
| Focuses on forged requests | Focuses on script execution |

An application can potentially be vulnerable to both.

---

## 12. Practical Debugging Checklist

If Spring Security returns a CSRF-related error:

```text
Request
  ↓
Is this a state-changing operation?
  ↓
Does Spring Security expect a CSRF token?
  ↓
Is the token included in the request?
  ↓
Is the token valid?
  ↓
Is the request using the expected HTTP method/content type?
```

For an HTML form, check:

- CSRF token is included.
- Form action points to the correct endpoint.
- HTTP method is correct.
- Spring Security CSRF protection has not been unintentionally disabled.
- The request is not being sent from a stale page/session.

---

## 13. Key Takeaways

- **CSRF = Cross-Site Request Forgery.**
- It targets authenticated users by tricking their browsers into making unwanted requests.
- Session cookies are automatically included by browsers, which is why cookie-based authentication is particularly relevant.
- CSRF tokens are a primary defense for browser-based applications.
- Spring Security provides CSRF protection.
- Do not disable CSRF blindly just to resolve a `403` or CSRF-token error.
- SameSite cookies provide additional protection.
- CSRF and XSS are different vulnerabilities and require different defenses.
- Whether CSRF protection is required depends on the application's authentication and browser interaction model.

### CSRF mental model

```text
Victim authenticated
        ↓
Attacker-controlled page
        ↓
Forged request
        ↓
Browser automatically sends credentials
        ↓
Server receives authenticated request
        ↓
CSRF protection checks token
        ↓
Valid token → Request allowed
Invalid/missing token → Request rejected
```