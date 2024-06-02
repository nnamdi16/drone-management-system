# DRONE MANAGEMENT SYSTEM
Welcome to the Drone Management System!. Drone management system is a Spring Boot REST-based application that simulates the management of a drone within a farm field.

## Table of Contents
* Getting Started
    * Prerequisites
    * Running the application
* API Endpoints
* Running Test
* Swagger Documentation


## Getting Started

### Prerequisite
- Java Development Kit [(JDK)](https://www.oracle.com/java/technologies/downloads/) 17 or higher
- Apache Maven 3.6.0 or higher
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=mac) with the Spring Boot plugin or any other suitable IDE that can run spring boot.
- [Docker](https://docs.docker.com/get-docker/) or you can set up your [postgres](https://www.postgresql.org/docs/current/tutorial-install.html) database locally.
- kubectl - (CLI  for accessing Kubernetes)

```bash 
 brew install kind
 brew install kubectl
 ```
- Ensure your Kind cluster is running:
```bash
kind get clusters
```


- If your cluster is not listed, you need to create or start it:
```bash
kind create clusters
```

- Check the current context and ensure it is set to the Kind cluster:
```bash
kubectl config current-context
```

The output should include a context related to Kind(e.g., `kind-kind`)

If necessary, switch to the correct context:

```bash
kubectl config use-context kind-kind
```

- Deploy the Kubernetes Dashboard
```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.5.1/aio/deploy/recommended.yaml
```

- Create a Service Account for Dashboard access
```bash
kubectl -n kubernetes-dashboard create token dashboard-admin-sa
kubectl create clusterrolebinding admin-user-binding --clusterrole=cluster-admin --serviceaccount=kubernetes-dashboard:admin-user
```

```bash
kubectl create ns istio

kubectl apply -f k8s/skaffold/postgres.yaml
```
[deployment.yaml](k8s%2Fskaffold%2Fdeployment.yaml)
Start the `kubectl` Proxy
Run `kubectl` proxy to access the dashboard:

```bash
kubectl proxy --port=8001
```

#### Access the Dashboard
```bash
http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/
```


### Running the application

#### Running the application via docker
Follow these steps to run the application:

- Clone the [repository](https://github.com/nnamdi16/drone-management-system.git)

```bash 
git clone https://github.com/nnamdi16/drone-management-system.git
cd drone-management-app
 ```

- Set up your PostgresSQL database and configure the database connection details in **src/main/resources/application.properties**.
In the application.properties file, the database configuration can be set as seen below:
```bash
spring.datasource.url=jdbc:postgresql://db:5432/drone-management-app
spring.datasource.username=postgres
spring.datasource.password=postgres
```
- Build the application using maven.

 ```bash
mvn clean compile jib:dockerBuild
   ```
 To load the docker image via kind
```bash
kind load docker-image drone-management-system
```



#### Running the application locally
  -  Set up your PostgresSQL database and configure the database connection details in **src/main/resources/application.properties**. 
  In the application.properties file, the database configuration can be set as seen below:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/drone-management-app
spring.datasource.username=postgres
spring.datasource.password=postgres
```

- Build the application using maven.

 ```bash
mvn clean install
   ```

```bash
mvn spring-boot:run
```

The app should now be running locally at http://localhost:8080 


## API Endpoints
The following API endpoints are available:

* POST /api/v1/drones: Register a drone.
* PUT /api/v1/drones/{id}: Move the drone.
* GET /api/v1/drones/{id}: Get the current drone position.
* GET /api/v1/drone?page=1&limit=50: Get all drone positions.


## Running Test
To run the test for the spring boot application, run the command below:

```bash
mvn clean install
```

To generate test coverage report for the spring boot application, run the command below:

```bash
cd drone-management-app
mvn jacoco:report
```

To view the report, you need to run the report, which is an index.html file in the target/site/jacoco folder in a browser.


## Documentation
The REST endpoints for the drone-management-app are documented using swagger.
The swagger documentation UI is seen below:
- [Drone Management App](http://localhost:8080/swagger-ui/index.html)



