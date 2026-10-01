# Spring Boot Actuator & DevTools

## 1. Introduction

Spring Boot provides tools that help developers **simplify application development** and **monitor application behavior**.

Two important components are:

- **Spring Boot DevTools** → improves the development experience and speeds up the development cycle.
- **Spring Boot Actuator** → provides production-ready monitoring and management capabilities.

> **Mental Model:**  
> DevTools → Development  
> Actuator → Monitoring & Management

---

## 2. Spring Boot DevTools

Spring Boot DevTools is a set of **development-time tools** designed to improve developer productivity and make the development cycle faster.

It is especially useful during development by reducing repetitive manual work when making and testing code changes.

### Key Features

#### Auto Restart

DevTools monitors changes to the application's **classpath** and automatically restarts the application when it detects a code change.

```text
Code Change
    ↓
Classpath Change Detected
    ↓
Application Automatically Restarts
```

This removes the need to manually stop and start the application after every code modification.

#### Live Reload

DevTools provides **Live Reload** functionality when used with a compatible browser plugin.

When changes are made to static resources such as:

- HTML
- CSS
- JavaScript

the browser can automatically refresh the page after the changes are detected.

```text
Change in Static Resource
        ↓
Change Detected
        ↓
Browser Automatically Refreshes
```

> **Remember:**  
> **Auto Restart** → restarts the Spring Boot application after code changes.  
> **Live Reload** → refreshes the browser after changes to static resources.

---

### Adding DevTools to a Spring Boot Project

To use Spring Boot DevTools, add the `spring-boot-devtools` dependency to the project.

#### Maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

#### Gradle

```gradle
dependencies {
    runtimeOnly 'org.springframework.boot:spring-boot-devtools'
}
```

Once DevTools is included, its development-time features such as **Auto Restart** and **Live Reload** can improve the development workflow.

> **Quick Recall:**  
> `spring-boot-devtools` → development-time productivity features.

---

## 3. Spring Boot Actuator

Spring Boot Actuator focuses on **monitoring and managing** a Spring Boot application, particularly in a **production environment**.

It provides a set of **production-ready features** that help developers understand what is happening inside the application.

Actuator exposes these capabilities through **endpoints** that can be accessed via HTTP.

```text
Spring Boot Application
        ↓
     Actuator
        ↓
     Endpoints
        ↓
Health | Metrics | Environment | Loggers | Info
```

Actuator endpoints can provide information about:

- Application health
- Application metrics
- Environment properties
- Logging configuration
- Application information

### Common Actuator Endpoints

| Endpoint | Purpose |
|---|---|
| `/actuator/health` | Provides information about the application's health |
| `/actuator/info` | Provides custom information about the application |
| `/actuator/metrics` | Exposes application metrics such as memory usage and garbage collection |
| `/actuator/env` | Displays environment properties and application configuration |
| `/actuator/loggers` | Allows viewing and dynamically modifying logger configuration |

### Quick Recall

```text
/actuator/health   → Application Health
/actuator/info     → Application Information
/actuator/metrics  → Application Metrics
/actuator/env      → Environment Properties
/actuator/loggers  → Logger Configuration
```

> **Mental Model:**  
> Spring Boot Actuator → production monitoring and management through HTTP endpoints.

---

### Custom Actuator Endpoints

Spring Boot Actuator also allows us to create **custom endpoints**.

Custom endpoints can expose application-specific information or functionality that is not covered by the built-in Actuator endpoints.

For example, we can create an endpoint that exposes the **application version**.

```java
@Component
@Endpoint(id = "appVersion")
public class AppVersionActuator {

    @ReadOperation
    public String getVersion() {
        return "1.0.0";
    }
}
```

### Understanding the Implementation

#### `@Component`

```java
@Component
```

Registers `AppVersionActuator` as a **Spring-managed bean**.

#### `@Endpoint`

```java
@Endpoint(id = "appVersion")
```

Defines a custom Actuator endpoint named `appVersion`.

#### `@ReadOperation`

```java
@ReadOperation
public String getVersion() {
    return "1.0.0";
}
```

Defines the operation performed when the endpoint is accessed for reading.

The `getVersion()` method returns the application's version.

```text
Custom Actuator Endpoint
          ↓
      @Endpoint
          ↓
     @ReadOperation
          ↓
    Application Version
```

The returned value can later be replaced with actual application-version logic, such as retrieving the version from a properties file, build tool, or another source.

> **Quick Recall:**  
> `@Endpoint` → defines the custom Actuator endpoint.  
> `@ReadOperation` → defines a read operation for the endpoint.  
> `@Component` → makes the class a Spring-managed bean.