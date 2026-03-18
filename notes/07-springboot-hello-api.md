# Hello API - Spring Boot

## What I Learned

- How to create a Spring Boot project using Maven
- Project structure (Application.java, controller)
- How to run application using mvn spring-boot:run
- Embedded Tomcat server runs on port 8080

## Key Concepts

- @SpringBootApplication → Entry point
- @RestController → Handles HTTP requests and returns JSON/text responses
- @GetMapping → Maps HTTP GET request
- Embedded Tomcat → Runs application without external server

## Endpoints Created

GET /hello → returns text  
GET /status → returns JSON

## Notes

- Controller handles HTTP requests
- Spring Boot auto-configures application based on dependencies
- Request flow: Client → Tomcat → Controller → Response