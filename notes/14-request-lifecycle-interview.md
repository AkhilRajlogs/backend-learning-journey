# Interview Questions

## Request Lifecycle in Spring Boot

When a client sends an HTTP request, it is first received by the embedded Tomcat server.

It is then forwarded to the DispatcherServlet, which acts as the front controller in Spring.

The DispatcherServlet uses HandlerMapping to find the appropriate controller method and HandlerAdapter to execute it.

If the request contains JSON, it is converted into a Java object using HttpMessageConverter and Jackson. Validation is triggered if @Valid is present.

The request then flows from Controller to Service, where business logic is executed, and the Service interacts with the Repository to access the database.

Finally, the response is sent back through the same path, converted to JSON, and returned to the client.

---

## POST Request Flow (Spring Boot - Task Manager API)

When a client sends a POST request, it is received by the embedded Tomcat server and forwarded to the DispatcherServlet.

The DispatcherServlet uses HandlerMapping to route the request to the appropriate controller method mapped using @PostMapping.

The request body is converted from JSON to a DTO using HttpMessageConverter and Jackson, and validation is triggered using @Valid.

If validation passes, the controller calls the Service layer, where business logic is executed.

The Service converts the DTO to an Entity and passes it to the Repository, which uses JPA to persist the data into the database.

The saved Entity is then converted back to a response DTO, returned through the same path, converted to JSON, and sent back to the client.