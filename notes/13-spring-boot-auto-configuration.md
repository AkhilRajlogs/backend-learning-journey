# Spring Boot Auto-Configuration

## Overview

This note explains how Spring Boot automatically configures common application components based on the project's dependencies and configuration, reducing the need for manual setup.

---

## What is Auto-Configuration?

Spring Boot automatically configures application components (beans) based on:

- Dependencies present in classpath
- Application properties

This reduces manual configuration.

---

## Auto-Configuration Process
  
### 1. @SpringBootApplication

This annotation includes:

- @Configuration → Defines beans
- @EnableAutoConfiguration → Enables auto-config
- @ComponentScan → Scans project classes

---

### 2. @EnableAutoConfiguration

- Uses AutoConfigurationImportSelector
- Loads configuration classes from:

META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports

---

### 3. Conditional Configuration

Auto-config classes use conditions:

- @ConditionalOnClass → Applies if class exists
- @ConditionalOnMissingBean → Applies if bean not defined
- @ConditionalOnProperty → Applies based on properties

---

## Key Principles

- Auto-configuration is activated based on project dependencies.
- Default beans are created only when no user-defined bean already exists.
- Application properties can customize the generated configuration.

---

## Example: spring-boot-starter-web

Automatically configures:

- DispatcherServlet
- Jackson (JSON support)
- Embedded Tomcat
- REST API support

---

## Overriding Auto-Configuration

You can override default behavior:

- Define your own @Bean
- Spring skips default configuration

---

## Disabling Auto-Configuration

You can disable specific auto-config:

```java
@SpringBootApplication(exclude = ClassName.class)
```

---

## Debugging Auto-Configuration

Enable debug logs:

`application.properties`

``` properties
debug=true
```

This shows which auto-configurations are applied or skipped.

---

## Related Notes

- 19 – Spring Annotations
- 10 – Spring Boot Request Lifecycle
- 12 – Spring MVC Internals