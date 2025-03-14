package com.jvlcode.spring_boot_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a source of bean definitions for the application context
@EnableWebSecurity	// Enables Spring Security's web security features and configuration
public class SecurityConfig {
    
    // Define a SecurityFilterChain bean to configure HTTP security
	// Once the SecurityFilterChain is defined and registered as a Spring bean, Spring Security will apply it automatically during the request-handling lifecycle.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        // Configure access control for HTTP requests
        http
        .authorizeHttpRequests(authz -> 
            // Allow all requests to /api/users/** without authentication
            authz.requestMatchers("/api/users/**").authenticated()
            .anyRequest().permitAll()
        )
        .formLogin( form -> form.permitAll());
        
        // Build and return the configured SecurityFilterChain
        return http.build();
    }
}
