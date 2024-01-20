# DRONE MANAGEMENT SYSTEM
Welcome to the Drone Management System!. Drone management system is a Spring Boot REST-based application that simulates the management of a drone within a farm field.

## Table of Contents
* Getting Started
    * Prerequisites
    * Running the application
* API Endpoints
* Running Test
* Swagger Documentation
* Assumptions



## Getting Started

### Prerequisite
- Java Development Kit [(JDK)](https://www.oracle.com/java/technologies/downloads/) 17 or higher
- Apache Maven 3.6.0 or higher
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=mac) with the Spring Boot plugin or any other suitable IDE that can run spring boot.
- [Docker](https://docs.docker.com/get-docker/) or you can set up your [postgres](https://www.postgresql.org/docs/current/tutorial-install.html) database locally.



### Running the application
Follow these steps to run the application:

- Clone the [repository](https://github.com/nnamdi16/drone-management-system.git)

```bash 
git clone https://github.com/nnamdi16/drone-management-system.git
cd drone-management-app
 ```
- Configure the database connection details in **src/main/resources/application.properties**.Set up your PostgresSQL database if you are not using docker.
- Build the application using maven.

```bash
mvn clean install
```

#### Run the Spring Boot application:
- Running the application using docker
```bash
docker-compose up
```

- Running the application locally
```bash
mvn spring-boot:run
```

The app should now be running locally at http://localhost:8080 


## API Endpoints
The following API endpoints are available:

* POST /api/v1/drones: Register a drone.
* PUT /api/v1/drones/{id}: Move the drone.
* GET /api/v1/drones/{id}: Get the current drone position.



## Running Test
#### Spring Boot Application
To run the test for the spring boot application, run the command below:

```bash
mvn clean test
```


## Documentation
The REST endpoints for the drone-management-app are documented using swagger.
The swagger documentation UI is seen below:
- [Drone Management App](http://localhost:8080/swagger-ui/index.html)


## Assumptions


