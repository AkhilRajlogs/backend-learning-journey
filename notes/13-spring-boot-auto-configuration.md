# Spring Boot Auto-Configuration

---

## What is Auto-Configuration?

Spring Boot automatically configures application components (beans) based on:

- Dependencies present in classpath
- Application properties

This reduces manual configuration.

---

## How It Works

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

## Key Behavior

- Dependency present → Configuration applied
- Bean already defined → Default config skipped

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

@SpringBootApplication(exclude = ClassName.class)

---

## Debugging Auto-Configuration

Enable debug logs:

application.properties:

debug=true

This shows which auto-configurations are applied or skipped.