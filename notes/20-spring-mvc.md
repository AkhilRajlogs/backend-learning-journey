# Spring MVC

## MVC Architecture

Spring MVC follows the Model-View-Controller pattern.

### Model

Contains the data that is passed to the view.

### View

Responsible for rendering the UI.

In this module, JSP is used as the view technology.

### Controller

Acts as the web layer.

It receives HTTP requests, delegates business logic to the service layer, prepares the Model, and returns a logical view name or response.

---

## Returning a View

A controller method can return a String representing the logical view name.

Example:

return "home";

Spring's ViewResolver resolves the logical view name to the configured JSP page.

---

## application.yml

Used to configure Spring Boot application properties.

Common configurations include:

- Server port
- View resolver properties
- Application-specific settings

---

## JSP

JavaServer Pages (JSP) is a server-side view technology that generates dynamic HTML before sending the response to the browser.

---

## JSTL

JSTL (JavaServer Pages Standard Tag Library) provides tags for:

- Iteration
- Conditional rendering
- Formatting
- Expression handling

---

## MVC Response Flow (Server-side Rendering)

Unlike a REST API, a Spring MVC controller can return a logical view name instead of JSON.

Example:

```java
return "home";
```

Flow:

Controller
    ↓
Logical View Name
    ↓
ViewResolver
    ↓
JSP
    ↓
Generated HTML
    ↓
Browser
  
### Model

The Model carries data from the controller to the view.

The JSP reads the model attributes and renders the final HTML page.
