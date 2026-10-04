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

---

## 4. Build Tools

Build tools are software applications that **automate the process of building, testing, and deploying software**.

They help developers manage complex codebases, reduce manual work, and maintain consistency throughout the software development lifecycle.

Build tools use predefined scripts and configurations to perform tasks such as:

- Compiling source code
- Managing dependencies
- Running tests
- Packaging applications for deployment

### Core Responsibilities of Build Tools

#### 1. Compilation

Build tools compile source code written in a high-level programming language into executable files or intermediate code.

```text
Source Code
    ↓
Compilation
    ↓
Executable / Intermediate Code
```

#### 2. Dependency Management

Build tools manage project dependencies such as libraries and external components.

They can automatically download, manage, and include required dependencies in the project.

#### 3. Testing

Build tools support automated testing to verify that the software functions as expected and to identify defects early.

This can include:

- Unit testing
- Integration testing

#### 4. Packaging

After compilation and testing, build tools package the application into a format suitable for deployment.

Common formats include:

- JAR (Java Archive)
- WAR (Web Application Archive)

> **Mental Model:**  
> Build Tools → Compile → Manage Dependencies → Test → Package

---

### Applications of Build Tools

Build tools automate important parts of the software development process.

#### Automated Builds

They automate the build process, reducing manual intervention and making builds more consistent and reproducible.

#### Dependency Management

They simplify the process of managing and incorporating external libraries and project dependencies.

#### Testing Automation

They automate testing, allowing tests to be executed quickly and consistently.

#### CI/CD

Build tools play an important role in **Continuous Integration and Continuous Deployment (CI/CD)** by automating build, test, and deployment processes.

```text
Code
 ↓
Build
 ↓
Test
 ↓
Deploy
```

#### Development Efficiency

By automating repetitive tasks, build tools allow developers to focus more on coding and innovation.

### Significance of Build Tools

Build tools provide several important benefits:

| Benefit | Purpose |
|---|---|
| **Error Reduction** | Reduces human errors in repetitive build and deployment tasks |
| **Efficiency** | Saves time and effort during development and deployment |
| **Reproducibility** | Helps produce the same output from the same source code |
| **Quality Assurance** | Automated testing and integration help improve software quality |
| **Consistency** | Helps maintain consistent builds across environments |
| **Scalability** | Helps manage growing codebases and project complexity |

> **Quick Recall:**  
> Build tools automate repetitive development tasks → improve efficiency → support testing and CI/CD → make builds more consistent and reproducible.

---

## 5. Interview & Quick Revision

### Core Mental Model

```text
Spring Boot Development & Operations
            ↓
    ┌───────┴────────┐
    ↓                ↓
DevTools          Actuator
    ↓                ↓
Development      Monitoring &
Productivity      Management

            +
            
        Build Tools
            ↓
   Build → Test → Package
```

### DevTools vs Actuator

| Feature | Spring Boot DevTools | Spring Boot Actuator |
|---|---|---|
| Primary purpose | Improve development productivity | Monitor and manage applications |
| Main environment | Development | Production |
| Key features | Auto Restart, Live Reload | Health, Metrics, Environment, Loggers |
| Access | Development tooling | HTTP endpoints |

### DevTools Quick Revision

**What is Spring Boot DevTools?**

> A set of development-time tools that improves developer productivity and speeds up the development cycle.

**What is Auto Restart?**

> DevTools automatically restarts the application when it detects relevant classpath changes.

**What is Live Reload?**

> It allows the browser to automatically refresh when changes to static resources such as HTML, CSS, or JavaScript are detected.

**What dependency enables DevTools?**

```text
spring-boot-devtools
```

---

### Actuator Quick Revision

**What is Spring Boot Actuator?**

> A Spring Boot component that provides production-ready features for monitoring and managing an application through endpoints.

**Important endpoints:**

```text
/actuator/health
    → Application health

/actuator/info
    → Application information

/actuator/metrics
    → Application metrics

/actuator/env
    → Environment properties

/actuator/loggers
    → Logger configuration
```

**Why is Actuator useful?**

> It provides insights into an application's runtime behavior, making it easier to monitor, manage, and troubleshoot production systems.

---

### Custom Actuator Endpoint

A custom endpoint can expose application-specific information or functionality.

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

**Key annotations:**

```text
@Component
    → Spring-managed bean

@Endpoint
    → Defines the custom Actuator endpoint

@ReadOperation
    → Defines a read operation
```

---

### Build Tools Quick Revision

**What are build tools?**

> Software applications that automate building, testing, and deploying software.

**Core responsibilities:**

```text
Compile
   ↓
Manage Dependencies
   ↓
Test
   ↓
Package
```

**What can build tools help with?**

- Automated builds
- Dependency management
- Testing automation
- CI/CD
- Packaging
- Consistent and reproducible builds

**Why are build tools important?**

> They reduce manual work and errors, improve efficiency and quality, support reproducibility and consistency, and help manage growing project complexity.

---

### Interview Questions

**1. What is the difference between DevTools and Actuator?**

> DevTools focuses on improving the development workflow, while Actuator focuses on monitoring and managing applications.

**2. What are the two important DevTools features covered here?**

> Auto Restart and Live Reload.

**3. What is the purpose of `/actuator/health`?**

> It provides information about the application's health.

**4. What does `/actuator/metrics` provide?**

> Application metrics such as memory usage and garbage collection information.

**5. Can we create our own Actuator endpoints?**

> Yes. Custom endpoints can expose application-specific information or functionality.

**6. Which annotations are used in the custom endpoint example?**

> `@Component`, `@Endpoint`, and `@ReadOperation`.

**7. What are build tools used for?**

> They automate tasks such as compilation, dependency management, testing, packaging, and deployment.

**8. How do build tools support CI/CD?**

> They automate build, test, and deployment processes within CI/CD pipelines.

---

### Final Memory Map

```text
DevTools
   ↓
Development Productivity
   ↓
Auto Restart + Live Reload

Actuator
   ↓
Application Monitoring & Management
   ↓
Health + Info + Metrics + Environment + Loggers
   ↓
Custom Endpoints

Build Tools
   ↓
Automated Software Development Tasks
   ↓
Compile + Dependencies + Test + Package
   ↓
CI/CD + Efficiency + Consistency + Reproducibility
```

> **One-Line Revision:**  
> **DevTools improves development, Actuator helps monitor and manage applications, and Build Tools automate the software build lifecycle.**