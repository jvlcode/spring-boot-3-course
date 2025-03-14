package com.jvlcode.spring_boot_demo.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvlcode.spring_boot_demo.entity.UserEntity;
import com.jvlcode.spring_boot_demo.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private JwtUtil jwtUtil;
	@Autowired
    private UserDetailsService userDetailsService;


	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody UserEntity user) {
	    try {
	        // Authenticate the user
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
	        );

	        // Retrieve authenticated user details
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        
	        // Generate JWT token
	        String token = jwtUtil.generateToken(userDetails);

	        // Return token in JSON response
	        return ResponseEntity.ok(Map.of("token", token));
	    } catch (AuthenticationException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
	    }
	}

}
