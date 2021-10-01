# Movie Character API
This is a Java Spring Web API demo for managing movies, characters and franchises.

# Database
The applications database is a simple Postgresql database which is auto generated by hibernate.  

# Data access
The data access is implemented with Hibernate and JPA repositories.

# REST API
The REST controllers are annotated to describe the API methods, the API documentation
is generated by Swagger in JSON format:
http://localhost:8080/v3/api-docs/

and in HTML: 
http://localhost:8080/swagger-ui/

## DTOs
The REST API uses Data Transfer Objects to hide the original business models.

# Service
The application service holds all repositories and is the central point for communication 
between the repositories and the controllers. 

