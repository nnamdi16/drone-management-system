package com.nnamdi.dronemanagementapp;

import com.nnamdi.dronemanagementapp.config.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DroneManagementAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DroneManagementAppApplication.class, args);
//        SpringApplication app = new SpringApplication(DroneManagementAppApplication.class);
//        app.addInitializers(new DotenvInitializer());
//        app.run(args);
    }

}
