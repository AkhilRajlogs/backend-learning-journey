# Spring Security - OAuth, OIDC & Keycloak

## 1. OAuth 2.0

OAuth 2.0 is an open standard framework for **authorization**.

It allows a client application to access protected resources on behalf of a user **without requiring the client to receive the user's username and password**.

### Authentication vs Authorization

- **Authentication** → "Who are you?"
- **Authorization** → "What are you allowed to access?"

OAuth 2.0 primarily deals with **authorization and delegated access**.

> **Mental Model:** OAuth = delegated authorization.

---

### OAuth 2.0 Actors

OAuth involves four main actors:

| Actor | Role |
|---|---|
| **Resource Owner** | Entity that owns the protected resources; usually the user |
| **Client** | Application requesting access to protected resources |
| **Authorization Server** | Authenticates/authorizes the user and issues tokens |
| **Resource Server** | Hosts the protected resources |

```text
Resource Owner
      ↓
 Grants permission
      ↓
    Client
      ↓
 Access Token
      ↓
Resource Server

Authorization Server
      ↓
Authenticates / authorizes
      ↓
Issues tokens
```

### Authorization Server vs Resource Server

```text
Authorization Server
        ↓
Authentication + Authorization
        ↓
Issues Access Tokens

Resource Server
        ↓
Hosts Protected Resources
        ↓
Uses Access Tokens for access
```

---

### OAuth Tokens

#### Access Token

An **Access Token** is a credential representing the authorization granted to the client.

The client uses it to access protected resources.

```text
Client
  ↓
Access Token
  ↓
Resource Server
  ↓
Protected Resource
```

#### Refresh Token

A **Refresh Token** can be used to obtain a new access token when the current access token expires, without requiring the user to authenticate again.

```text
Access Token
     ↓
  Expires
     ↓
Refresh Token
     ↓
New Access Token
```

> **Remember:** Access Token → access resources.  
> Refresh Token → obtain a new Access Token.

---

### OAuth Grant Types

A grant type defines how a client obtains an access token.

#### 1. Authorization Code

The client receives an **authorization code** and exchanges it for an access token.

```text
User
 ↓
Authorization Server
 ↓
Authentication + Consent
 ↓
Authorization Code
 ↓
Client
 ↓
Token Request
 ↓
Access Token
```

#### 2. Implicit Grant

The access token is returned directly after authorization rather than first returning an authorization code.

The CN material describes this as a flow suitable for client-side applications such as JavaScript applications.

```text
User
 ↓
Authorization Server
 ↓
Authorization
 ↓
Access Token
 ↓
Client
```

#### 3. Client Credentials

Used for **server-to-server communication where no user is involved**.

```text
Client
 ↓
Authorization Server
 ↓
Client Authentication
 ↓
Access Token
 ↓
Resource Server
```

> **Quick Recall**
>
> - Authorization Code → user-based authorization
> - Implicit → token returned directly
> - Client Credentials → server-to-server, no user

---

### OAuth Workflow

The general OAuth workflow is:

```text
1. Client Registration
          ↓
2. Authorization Request
          ↓
3. User Authentication + Consent
          ↓
4. Token Request
          ↓
5. Access Protected Resources
```

#### 1. Client Registration

The client registers with the authorization server and receives client credentials such as:

```text
Client ID
Client Secret
```

#### 2. Authorization Request

The client requests authorization and redirects the user to the authorization server.

#### 3. User Authentication and Consent

The user authenticates and grants or denies the requested access.

```text
User
 ↓
Authenticate
 ↓
Grant / Deny Permission
```

#### 4. Token Request

If authorization is granted, the client exchanges the authorization grant for an access token.

For Authorization Code flow:

```text
Authorization Code
        ↓
Token Request
        ↓
Access Token
```

#### 5. Access Protected Resources

The client uses the access token to access resources on the resource server.

```text
Client
  ↓
Access Token
  ↓
Resource Server
  ↓
Protected Resource
```

---

### OAuth Security Considerations

Important security considerations include:

- **Protect tokens** from interception, leakage, and unauthorized use.
- **Secure storage and transmission** of credentials and tokens.
- **Manage token expiration and refresh** securely.
- **Protect the Authorization Server**, since it handles authentication, authorization, and token issuance.

```text
OAuth Security
     ↓
Protect Tokens
     +
Secure Storage / Transmission
     +
Secure Expiration / Refresh
     +
Secure Authorization Server
```

---

### OAuth Mental Model

```text
User
(Resource Owner)
      ↓
Grants Permission
      ↓
Authorization Server
      ↓
Issues Token
      ↓
Client
      ↓
Sends Access Token
      ↓
Resource Server
      ↓
Protected Resource
```

> **Core Idea:** OAuth 2.0 allows a client to obtain limited, token-based access to protected resources on behalf of a resource owner without requiring the client to receive the user's credentials.

---

### Interview Quick Revision

**What is OAuth 2.0?**

> OAuth 2.0 is an authorization framework that allows a client application to obtain limited access to protected resources on behalf of a resource owner without requiring the client to receive the user's credentials.

**What problem does OAuth solve?**

> OAuth enables delegated access. A user can grant an application limited access to protected resources without sharing their username and password with that application.

**What are the four OAuth actors?**

```text
Resource Owner
Client
Authorization Server
Resource Server
```

**What is an Access Token?**

> A credential representing the authorization granted to a client to access protected resources.

**What is a Refresh Token?**

> A token used to obtain a new access token after the current access token expires.

**What is the difference between Authorization Server and Resource Server?**

```text
Authorization Server
→ Authenticates / authorizes
→ Issues tokens

Resource Server
→ Hosts protected resources
→ Provides access based on authorization
```

**What is an Authorization Code?**

> An authorization grant that the client exchanges for an access token.

**What is Client Credentials?**

> A grant used for server-to-server communication where no user is involved.

**Is OAuth authentication or authorization?**

> OAuth 2.0 primarily provides **authorization** and delegated access to resources. OIDC adds an identity/authentication layer on top of OAuth 2.0.