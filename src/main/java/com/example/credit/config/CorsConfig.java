package com.example.credit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins:http://192.168.33.10:4200,http://localhost:4200}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/web/**")  
                .allowedOrigins(Arrays.stream(allowedOrigins.split(","))
                    .map(String::trim)  // Supprime les espaces
                    .toArray(String[]::new))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
