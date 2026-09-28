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