## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
This project is client-server Web application "Checkers game"

## Technologies
Project is created with:
* Spring boot, version: 2.3
* React, version: 16.13

## Setup
1. Install SQL DB and configure connection.
The credentials of the connection should be updated in the application.propreties file:
spring.datasource.url=***your DB url***
spring.datasource.username=***your DB username***
spring.datasource.password=***your DB password***

2. Run the application with the command:
```
$mvnw spring-boot:start
```

3. The application can be accessed through the following link: http://localhost:8080

