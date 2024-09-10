package com.nnamdi.dronemanagementapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@SpringBootApplication
public class DroneManagementAppApplication {

    public static void main(String[] args) {
//        SpringApplication.run(DroneManagementAppApplication.class, args);
        SpringApplication app = new SpringApplication(DroneManagementAppApplication.class);
        app.addInitializers(applicationContext -> {
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            Path path = Paths.get("config/service-auth-cfg");
            try {
                Properties properties = new Properties();
                properties.load(Files.newInputStream(path));
                System.out.println(properties);
                PropertiesPropertySource propertySource = new PropertiesPropertySource("application.properties", properties);
                environment.getPropertySources().addLast(propertySource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        app.run(args);
    }


}
