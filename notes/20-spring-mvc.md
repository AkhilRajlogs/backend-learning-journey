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

It receives HTTP requests, processes them, interacts with the service layer, and returns a view name or response.

---

## Returning a View

A controller method can return a String representing the logical view name.

Example:

return "home";

Spring resolves the logical view name to the configured JSP page.

---

## application.yml

Used to configure Spring Boot application properties.

Example uses:

- Server port
- View resolver configuration
- Application settings

---

## JSP

JavaServer Pages (JSP) is a server-side view technology used to generate dynamic HTML.

---

## JSTL

JSTL (JavaServer Pages Standard Tag Library) provides tags for:

- Iteration
- Conditional rendering
- Formatting
- Expression handling