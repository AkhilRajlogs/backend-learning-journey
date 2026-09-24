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

---

## 2. OpenID Connect (OIDC)

**OpenID Connect (OIDC)** is an **identity layer built on top of OAuth 2.0**.

OAuth 2.0 primarily handles **authorization**, while OIDC adds standardized **authentication and identity information**.

> **Mental Model:** OAuth = "What can this client access?"  
> OIDC = "Who is the authenticated user?"

---

### OIDC and OAuth 2.0

```text
OAuth 2.0
   ↓
Authorization / Delegated Access
   ↓
OIDC
   ↓
Authentication + Identity Information
```

OIDC uses OAuth 2.0 mechanisms and adds an **ID Token** containing claims about the authenticated user.

---

### ID Token

The **ID Token** is a **JWT (JSON Web Token)** that contains claims about the authenticated end-user.

It allows the client to receive standardized identity information about the user.

```text
User
 ↓
Authentication
 ↓
Authorization Server
 ↓
ID Token (JWT)
 ↓
Client
 ↓
User Identity Information
```

> **Remember:**  
> **Access Token → access protected resources**  
> **ID Token → information about the authenticated user's identity**

---

### OIDC Endpoints

OIDC commonly involves three important endpoints:

| Endpoint | Purpose |
|---|---|
| **Authorization Endpoint** | Used to authenticate the user and obtain authorization |
| **Token Endpoint** | Used to obtain tokens |
| **UserInfo Endpoint** | Provides information/claims about the authenticated user |

```text
Client
  ↓
Authorization Endpoint
  ↓
User Authentication + Consent
  ↓
Token Endpoint
  ↓
ID Token + Access Token
  ↓
UserInfo Endpoint
  ↓
User Information
```

---

### OIDC Flows

The CN material describes two OIDC flows:

#### 1. Authorization Code Flow

The client receives an authorization code and exchanges it for tokens.

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
Token Endpoint
 ↓
ID Token + Access Token
```

#### 2. Implicit Flow

The tokens are returned directly after authorization rather than first returning an authorization code.

```text
User
 ↓
Authorization Server
 ↓
Authentication + Authorization
 ↓
Tokens
 ↓
Client
```

> **Quick Recall**
>
> - Authorization Code → authorization code → token exchange
> - Implicit → tokens returned directly

---

### Claims and Scopes

#### Claims

**Claims** are pieces of information about the authenticated user.

Examples include:

```text
Name
Email
Profile Information
```

The ID Token contains claims about the authenticated end-user.

#### Scopes

**Scopes** define the requested level/type of access or identity information.

```text
Client
 ↓
Requests Scopes
 ↓
User Authentication + Consent
 ↓
Tokens / Claims
```

> **Remember:** Scope describes **what is being requested**; claims represent **information about the user**.

---

### OIDC Authentication & Consent

OIDC involves:

1. Client requests authentication.
2. User authenticates with the Identity Provider.
3. User provides consent where required.
4. Authorization Server issues an **ID Token** and **Access Token**.
5. Client can use the UserInfo endpoint to obtain user information.

```text
Client
  ↓
Authentication Request
  ↓
User Authentication
  ↓
Consent
  ↓
ID Token + Access Token
  ↓
UserInfo
  ↓
User Information
```

---

### OIDC Workflow

```text
1. Client Registration
          ↓
2. Authentication Request
          ↓
3. User Authentication + Consent
          ↓
4. ID Token + Access Token Issued
          ↓
5. Access User Information
          ↓
   UserInfo Endpoint
```

The important addition compared with OAuth 2.0 is that OIDC provides a standardized way for the client to receive **identity information about the authenticated user**.

---

### Advantages of OIDC

OIDC provides:

- **Standardized authentication and identity information**
- **Single Sign-On (SSO)**
- A **user-centric identity framework**
- Integration with OAuth 2.0 for authorization and delegated access

```text
OAuth 2.0
Authorization
      +
OIDC
Authentication + Identity
      ↓
Modern Identity / Access Flow
```

---

### OAuth vs OIDC — Interview Revision

| OAuth 2.0 | OIDC |
|---|---|
| Authorization framework | Identity/authentication layer |
| Delegated access | Authentication + identity |
| Access Token | ID Token + Access Token |
| Access Token used for protected resources | ID Token contains user identity claims |
| Answers: "What can the client access?" | Answers: "Who is the user?" |

> **Interview Tip:** OAuth 2.0 by itself should not be treated as a standardized user-authentication protocol. OIDC builds an identity layer on OAuth 2.0 for authentication.

### Interview Quick Revision

**What is OIDC?**

> OpenID Connect is an identity layer built on OAuth 2.0 that provides authentication and standardized identity information about the authenticated user.

**What is an ID Token?**

> An ID Token is a JWT containing claims about the authenticated end-user.

**What is the difference between an Access Token and an ID Token?**

```text
Access Token
→ Used to access protected resources

ID Token
→ Contains identity information about the authenticated user
```

**What are the main OIDC endpoints?**

```text
Authorization Endpoint
Token Endpoint
UserInfo Endpoint
```

**What are claims?**

> Claims are pieces of information about the authenticated user, such as name, email, and profile information.

**What is the relationship between OAuth 2.0 and OIDC?**

> OAuth 2.0 provides authorization and delegated access, while OIDC adds an authentication and identity layer on top of OAuth 2.0.

---

## 3. Keycloak

**Keycloak** is an open-source **Identity and Access Management (IAM)** solution used for authentication, authorization, and user management.

It supports standards such as **OAuth 2.0, OIDC, and SAML** and can integrate with applications and external identity providers.

> **Mental Model:** Keycloak = centralized Identity & Access Management.

---

### Keycloak Capabilities

Keycloak provides several features for managing application identity and access.

#### Authentication

Supports:

- User authentication
- **Single Sign-On (SSO)**
- **Multi-Factor Authentication (MFA)**
- Custom authentication flows

MFA can use mechanisms such as:

- OTPs
- WebAuthn
- Custom authentication flows

#### User Management

Keycloak provides:

- User provisioning and deprovisioning
- User directory
- Groups
- Role-Based Access Control (RBAC)

```text
Users
  ↓
Groups / Roles
  ↓
Permissions
  ↓
Application Resources
```

#### Authorization

Keycloak supports fine-grained authorization and can secure APIs using:

- OAuth 2.0
- OIDC
- Resource-based authorization policies

#### Identity Federation & Social Login

Keycloak can integrate with external identity systems and social providers.

Examples include:

```text
Identity Federation
├── SAML
├── OpenID Connect
└── Kerberos

Social Login
├── Google
├── Facebook
├── Twitter
└── Other Providers
```

#### Customization

Keycloak allows customization of:

- Authentication flows
- User-facing pages
- Branding
- Themes

---

### Keycloak Integrations

Keycloak supports integration with technologies and identity standards such as:

```text
Identity Standards
├── OAuth 2.0
├── OpenID Connect
└── SAML

Directory / Authentication
└── LDAP

Applications / Platforms
├── Spring Boot
├── Node.js
└── Kubernetes
```

---

### Keycloak Architecture

The CN material identifies four main architectural components:

| Component | Purpose |
|---|---|
| **Keycloak Server** | Handles authentication, authorization, and identity management |
| **Adapters** | Help applications integrate with Keycloak |
| **Admin Console** | Administrative interface for managing Keycloak |
| **Database** | Stores Keycloak data such as users and configuration |

```text
                    Keycloak
                       │
       ┌───────────────┼───────────────┐
       ↓               ↓               ↓
 Keycloak Server    Admin Console    Database
       │
       ↓
   Adapters
       │
       ↓
 Applications
```

---

### Developer & Administration Tools

Keycloak provides tools for managing and integrating the identity system:

- **Admin Console** → manage users, authentication, roles, and configuration
- **REST API** → programmatic administration and integration
- **SPIs (Service Provider Interfaces)** → extend/customize Keycloak functionality

---

### Security & Compliance

Keycloak provides security-related capabilities including:

- **Audit logs**
- **Encryption**
- Support for established security standards

These features help organizations manage and monitor identity and access securely.

---

### Keycloak Use Cases

Keycloak can be used for:

#### Enterprise Identity Management

Centralized authentication and authorization across enterprise applications.

#### Customer Identity and Access Management (CIAM)

Managing authentication and identity for customer-facing applications.

#### API Security

Protecting APIs using OAuth 2.0/OIDC-based access control.

#### B2B / Partner Collaboration

Managing authentication and access for external partners and organizations.

```text
Keycloak
    ↓
Centralized Identity
    ↓
┌──────────┬──────────┬──────────┬──────────┐
│ Web App  │ API      │ Mobile   │ Partner  │
│          │          │ App      │ System   │
└──────────┴──────────┴──────────┴──────────┘
```

---

### Why Keycloak?

Without a centralized identity solution, each application may need to implement and maintain its own:

```text
User Management
Authentication
Authorization
Password Management
MFA
SSO
```

Keycloak provides a centralized platform for these identity and access-management responsibilities.

> **Core Idea:** Keycloak centralizes identity and access management so applications can rely on a dedicated identity system instead of implementing all authentication functionality themselves.

---

### OAuth 2.0 + OIDC + Keycloak

These concepts fit together as:

```text
OAuth 2.0
   ↓
Authorization / Delegated Access

OIDC
   ↓
Authentication + Identity

Keycloak
   ↓
Identity & Access Management Platform
   ↓
Implements / Supports OAuth 2.0 + OIDC
```

> **Remember:** OAuth 2.0 and OIDC are **protocol/framework standards**, while Keycloak is an **IAM product/platform** that can implement and manage these identity and access flows.

---

### Interview Quick Revision

**What is Keycloak?**

> Keycloak is an open-source Identity and Access Management solution that provides authentication, authorization, and user management.

**What features does Keycloak provide?**

> Keycloak provides SSO, MFA, user management, groups, RBAC, fine-grained authorization, identity federation, social login, customizable authentication flows, and administrative/developer tools.

**What is Keycloak used for?**

> It can centralize identity and access management for enterprise applications, customer-facing applications, APIs, and B2B/partner systems.

**Which standards can Keycloak integrate with?**

```text
OAuth 2.0
OpenID Connect
SAML
```

It can also integrate with directory/authentication systems such as LDAP and external identity providers.

**What is the difference between Keycloak and OIDC?**

```text
OIDC
→ Identity/authentication protocol

Keycloak
→ IAM platform that supports OIDC
```

**What is SSO in Keycloak?**

> Single Sign-On allows users to authenticate through a centralized identity system and access multiple applications without separately authenticating with each application.

**What is RBAC?**

> Role-Based Access Control assigns permissions based on roles associated with users or groups.

**What are the main Keycloak architectural components mentioned in the CN material?**

```text
Keycloak Server
Adapters
Admin Console
Database
```

---

## 4. Social Login

**Social Login** allows users to authenticate using an existing account from an external identity provider such as **Google or GitHub**.

The application delegates authentication to the external provider instead of requiring the user to create and manage a separate application password.

```text
User
 ↓
Application
 ↓
External Identity Provider
 ↓
Authentication
 ↓
Application
 ↓
Authenticated User
```

---

### Sign in with Google

Google Login allows users to authenticate using their Google account.

The CN material describes Google Sign-In as an **OAuth 2.0-based integration** that can use authorization flows and Google Sign-In APIs/SDKs.

Typical user information that may be obtained includes:

```text
Name
Email
Profile Picture
```

The application requests appropriate **scopes** to obtain the required information.

---

### Google Login Flow

The general process is:

```text
1. User clicks "Sign in with Google"
             ↓
2. Application redirects user to Google
             ↓
3. User authenticates with Google
             ↓
4. User grants requested permissions
             ↓
5. Google returns authorization information
             ↓
6. Application validates/exchanges the information
             ↓
7. Application establishes authenticated user session
```

The CN material describes both client-side and server-side responsibilities:

```text
Client Side
→ Display Google Sign-In / Login button

Server Side
→ Handle authorization response
→ Exchange authorization information when required
→ Validate information with Google
→ Authenticate the user
```

---

### Benefits of Google Login

Social login can provide:

- Convenient user authentication
- Reduced need for users to manage another password
- SSO-style experience through the external identity provider
- Access to permitted profile information
- Faster onboarding for users

---

### Sign in with GitHub

GitHub can also be used as an OAuth-based login provider.

The CN material demonstrates GitHub login using Spring Security OAuth2 configuration.

#### 1. Create a GitHub Application

Register the application with GitHub and obtain:

```text
Client ID
Client Secret
```

#### 2. Configure `application.yml`

Store the GitHub OAuth client configuration:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            clientId: ${GITHUB_CLIENT_ID}
            clientSecret: ${GITHUB_CLIENT_SECRET}
```

> **Security:** Keep the client secret outside source code where possible. Environment variables are used here instead of hard-coding the secret.

#### 3. Configure Spring Security

The security configuration is modified to enable OAuth2 login.

The application can then use Spring Security's OAuth2 support to handle the GitHub authentication flow.

#### 4. Add Login Option

The login page can provide a GitHub login option:

```html
<a href="/oauth2/authorization/github">
    Login with GitHub
</a>
```

The request starts the OAuth2 authorization flow through Spring Security.

---

### Social Login Flow

The common pattern can be summarized as:

```text
User
 ↓
Application Login Page
 ↓
Choose Google / GitHub
 ↓
External Identity Provider
 ↓
User Authentication + Consent
 ↓
Authorization Response
 ↓
Application / Spring Security
 ↓
Authenticated User
```

---

### Google vs GitHub Login

| Google Login | GitHub Login |
|---|---|
| Google acts as external identity provider | GitHub acts as external identity provider |
| OAuth-based integration | OAuth-based integration |
| Can provide profile information based on scopes | Can provide permitted GitHub user information |
| Requires Google application/client configuration | Requires GitHub application/client configuration |

The underlying idea is the same:

```text
External Provider
       ↓
Authenticate User
       ↓
Return Authorization Information
       ↓
Application
       ↓
Authenticated User
```

---

### Social Login — Interview Quick Revision

**What is Social Login?**

> Social Login allows users to authenticate through an external identity provider such as Google or GitHub instead of maintaining a separate application password.

**Why use Social Login?**

> It simplifies user authentication and onboarding by allowing users to use an existing identity-provider account.

**What information can Google provide after authentication?**

> Depending on the requested scopes and permissions, information such as the user's name, email, and profile picture can be obtained.

**What credentials are required to integrate GitHub OAuth?**

```text
Client ID
Client Secret
```

**Where should the GitHub client secret be stored?**

> It should not be hard-coded in source code. The CN example uses an environment variable:

```yaml
clientSecret: ${GITHUB_CLIENT_SECRET}
```

**How does Spring Security start GitHub OAuth login?**

```text
/login page
     ↓
/oauth2/authorization/github
     ↓
GitHub
     ↓
Authentication
     ↓
Application
```

**What is the common idea behind Google and GitHub Login?**

> Both delegate authentication to an external identity provider and allow the application to establish an authenticated user after the OAuth-based flow completes.