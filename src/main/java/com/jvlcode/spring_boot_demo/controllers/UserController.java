package com.jvlcode.spring_boot_demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvlcode.spring_boot_demo.entity.UserEntity;
import com.jvlcode.spring_boot_demo.exceptions.ResourceNotFoundException;
import com.jvlcode.spring_boot_demo.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	// ✅ GET (Get All Users)
	@GetMapping
	public List<UserEntity> getUsers() {
//		return Arrays.asList(new User(1L, "Logesh", "logesh@gmail.com"));
		return userRepository.findAll();
		
	}
	
	// ✅ POST (Create New User)
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userRepository.save(user);
    }
    
    // ✅ GET (Get Single User)
    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // ✅ PUT (Update User by ID)
    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
    	UserEntity userData = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    	userData.setEmail(user.getEmail());
    	userData.setName(user.getName());
    	return userRepository.save(userData);
    }

    // ✅ DELETE (Delete User by ID)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    	UserEntity userData = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    	userRepository.delete(userData);
    	return ResponseEntity.ok().build();
    }
}
