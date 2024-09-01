package com.roleapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roleapi.authenticate.JwtUtil;
import com.roleapi.entities.User;
import com.roleapi.payloads.AuthenticationRequest;
import com.roleapi.payloads.AuthenticationResponse;
import com.roleapi.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest)
			throws Exception {
		this.authenticate(authRequest.getEmail(), authRequest.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

		String jwt = jwtUtil.generateToken(userDetails);

		AuthenticationResponse response = new AuthenticationResponse();
		response.setJwt(jwt);

		return ResponseEntity.ok(response);
	}

	private void authenticate(String email, String password) throws Exception {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				email, password);
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details !!");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser = userService.save(user);
		return ResponseEntity.ok(savedUser);
	}
}
