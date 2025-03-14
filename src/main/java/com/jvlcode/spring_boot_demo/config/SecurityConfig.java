package com.jvlcode.spring_boot_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
            .requestMatchers("/home").authenticated()
            .anyRequest().permitAll()
        )
        .formLogin(form -> form
        		.defaultSuccessUrl("/home")
                .permitAll()
            );
        
        // Build and return the configured SecurityFilterChain
        return http.build();
    }
    
    /**
     * Defines in-memory user authentication
     * - Creates two users: "user" and "admin" with different roles
     * - Passwords are encrypted using BCrypt for better security
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Create an in-memory user (Role: USER)
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("user123")) // Securely store password
                .roles("USER") // Assign "USER" role
                .build();

        // Create an in-memory admin (Role: ADMIN)
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin123")) // Securely store password
                .roles("ADMIN") // Assign "ADMIN" role
                .build();

        // Return a user manager that holds both users
        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     * Defines password encoder
     * - BCryptPasswordEncoder hashes passwords for security
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Securely encode passwords
    }	
}
