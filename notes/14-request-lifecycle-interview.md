#INTERVIEW QUESSTIONS  
  
## Explain request lifecycle in Spring Boot:  
    
When a client sends an HTTP request, it is first received by the embedded Tomcat server.
It is then forwarded to the DispatcherServlet, which acts as the front controller in Spring.

The DispatcherServlet uses HandlerMapping to find the appropriate controller method and HandlerAdapter to execute it.

If the request contains JSON, it is converted into a Java object using HttpMessageConverter and Jackson. Validation is triggered if @Valid is present.

The request then flows from Controller to Service, where business logic is executed, and the Service interacts with the Repository to access the database.

Finally, the response is sent back through the same path, converted to JSON, and returned to the client

---