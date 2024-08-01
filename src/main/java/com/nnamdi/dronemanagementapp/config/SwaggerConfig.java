package com.nnamdi.dronemanagementapp.config;

import com.nnamdi.dronemanagementapp.util.PropertySourceResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Swagger configuration
 *
 * @author Nwabuokei Nnamdi
 */
@Configuration
public class SwaggerConfig {
    PropertySourceResolver propertySourceResolver;

    @Autowired
    public SwaggerConfig(PropertySourceResolver propertySourceResolver) {
        this.propertySourceResolver = propertySourceResolver;
    }

    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact();
        contact.email("nwabuokeinnamdi19@gmail.com");
        contact.name("Nwabuokei Nnamdi");

        Info info = new Info()
                .title(propertySourceResolver.getProjectName())
                .version(propertySourceResolver.getProjectVersion())
                .contact(contact)
                .description(propertySourceResolver.getProjectDescription());
        return new OpenAPI().info(info);

    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
