package com.mediscreen.gatewayservice.controller;

import com.mediscreen.gatewayservice.model.UserCredential;
import com.mediscreen.gatewayservice.service.DemoAuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoAuthController {

    private final DemoAuthenticationService authService;

    public DemoAuthController(DemoAuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserCredential credentials) {
        return authService.authenticate(credentials.getUsername(), credentials.getPassword());
    }
}