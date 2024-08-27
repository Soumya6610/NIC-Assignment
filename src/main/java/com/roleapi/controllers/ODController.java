package com.roleapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/OD")
public class ODController {

	@GetMapping("/dashboard")
    public ResponseEntity<String> ODDashboard() {
        return ResponseEntity.ok("Welcome to OD Dashboard");
    }
	
}
