package com.mediscreen.gatewayservice.controller;

import com.mediscreen.gatewayservice.model.UserCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LoginController {

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredential credentials) {
        if (credentials.getUsername().equals(username) && credentials.getPassword().equals(password)) {
            return ResponseEntity.ok().body("Login successful");
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

}

