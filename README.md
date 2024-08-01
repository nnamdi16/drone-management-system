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
- kubectl- (CLI for accessing Kubernetes)


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

- Deploy both the PostgreSQL container and the drone-management container.

 ```bash
docker-compose up -d
   ```

- To check if the docker image is running.

 ```bash
docker ps
   ```


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
kubectl apply -f k8s/kind/dashboard-adminuser.yaml  
kubectl apply -f k8s/kind/cluster-role-binding.yaml 
```

- Create a token to access the Kubernetes Dashboard
```bash
kubectl -n kubernetes-dashboard create token admin-user 
```

To load the docker image via kind
```bash
kind load docker-image drone-management-system:latest
```

- To deploy the spring boot application using Helm
```bash
helm create drone-management-system
```

- To package the helm chart
```bash
helm package drone-management-system
```

- To install the helm chart to the kubernetes cluster
```bash
helm install drone-management-system ./drone-management-system
```

- To check the status of the helm release
```bash
helm status drone-management-system 

- To upgrade the existing helm release
```bash
helm upgrade drone-management-system ./drone-management-system

```
- To debug the helm chart template
```bash
helm template drone-management-system ./drone-management-system --debug  
```
- To check the service configuration of drone management-system
```bash
kubectl get svc drone-management-system -o yaml

```

- To access the API after deployment, temporarily forward the port to your localhost for testing:
```bash
kubectl port-forward svc/drone-management-system 5000:8080


```

- Then try to access the application via curl:
```bash
curl http://localhost:5000/api/v1/drone


```


- To create and read the config map from service.auth.cfg:
  - The file is referenced in the value.yaml file
  - To view the details of the config map
```bash
kubectl get configmaps 
kubectl describe configmap drone-management-system-config 


```


```bash
kubectl create ns istio

kubectl apply -f k8s/skaffold/postgres.yaml
kubectl apply -f k8s/skaffold/deployment.yaml
```

Start the `kubectl` Proxy
Run `kubectl` proxy to access the dashboard:

```bash
kubectl proxy --port=8001
```

#### Access the Dashboard
```bash
http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/
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



