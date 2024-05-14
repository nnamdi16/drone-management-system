FROM maven:3.9.6-amazoncorretto-17
WORKDIR /drone-management-system
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]