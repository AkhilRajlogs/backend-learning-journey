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