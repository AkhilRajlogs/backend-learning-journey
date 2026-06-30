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
  
---

## MVC Layer Responsibilities

A typical Spring MVC application is organized into multiple layers.

### Web Layer

- Handles HTTP requests
- Implemented using Controllers
- Interacts with the Service layer

### Service Layer

- Contains business logic
- Coordinates application operations
- Interacts with the persistence layer

### Persistence Layer (DAO)

- Responsible for database operations
- Encapsulates data access logic

### Domain Model

Represents the application's business entities.

These objects carry the application's core data.

### UI Model

Used to transfer data from the controller to the view.

The view reads model attributes to render dynamic content.

### View (JSP)

Responsible for generating the HTML returned to the browser.

---

## Passing Data Between Controller and View

Spring MVC provides multiple ways to pass data from a controller to a view.

### Model

- Stores attributes to be rendered by the view
- Commonly used in controller methods

### ModelMap

- Similar to Model
- Provides a map-like structure for storing model attributes

### ModelAndView

Combines:

- Model (data)
- View (logical view name)

Allows returning both the model data and the view from a single object.

---

## Request Parameters

Spring MVC can bind request parameters from the URL to controller method parameters.

Example:

```/search?keyword=spring```


`keyword` is extracted from the request and made available to the controller.