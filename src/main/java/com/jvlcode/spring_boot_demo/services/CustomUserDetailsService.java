package com.jvlcode.spring_boot_demo.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jvlcode.spring_boot_demo.entity.UserEntity;
import com.jvlcode.spring_boot_demo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Fetch user from the database
//        UserEntity user = userRepository.findByUsername(username)
//            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        // Return user details to Spring Security
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                Collections.singleton(new SimpleGrantedAuthority("USERROLE"))
//        );
//    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

     // No roles, just return username & password
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), 
                // Provide an empty list of authorities
                java.util.Collections.emptyList()
        );
    }
}
