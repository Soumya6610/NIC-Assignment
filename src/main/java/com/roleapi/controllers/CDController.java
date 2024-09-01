package com.roleapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cd")
public class CdController {

	@GetMapping("/dashboard")
	public ResponseEntity<String> getCdDashboard() {
		return ResponseEntity.ok("Welcome to the CD Dashboard");
	}

}
