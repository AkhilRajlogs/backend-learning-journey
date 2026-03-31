# Backend Architecture Notes

## Why not use Entity directly in API?

- Exposes internal database structure
- Creates tight coupling between API and database
- Reduces flexibility in changing schema
- May expose sensitive fields

---

## Why DTO is used?

- Controls request and response structure
- Improves security
- Decouples API from database
- Supports validation at API layer

---

## What should NOT be inside Service layer?

- HTTP request/response handling
- Annotations like @RequestMapping, @PathVariable
- Direct dependency on web layer

---

## What does Service layer do?

- Contains business logic
- Coordinates between layers
- Applies rules and validations beyond basic input validation

---

## What is DispatcherServlet?

- Front controller in Spring MVC
- Receives all incoming requests
- Uses HandlerMapping to route to correct controller
- Uses HandlerAdapter to execute controller method

---

## Request Flow (Detailed)

Client  
→ Tomcat  
→ DispatcherServlet  
→ HandlerMapping  
→ Controller  
→ Service  
→ Repository  
→ Database  
→ Response (reverse flow)