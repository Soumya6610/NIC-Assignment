package com.roleapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roleapi.entity.User;
import com.roleapi.repo.UserRepo;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		return ResponseEntity.ok("User registered successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user) {
		User foundUser = userRepo.findByEmail(user.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
			return ResponseEntity.ok(foundUser);
		} else {
			return ResponseEntity.status(401).body(null);
		}
	}
}
