package com.roleapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/CD")
public class CDController {

	@GetMapping("/dashboard")
    public ResponseEntity<String> CDDashboard() {
        return ResponseEntity.ok("Welcome to CD Dashboard");
    }
	
}
