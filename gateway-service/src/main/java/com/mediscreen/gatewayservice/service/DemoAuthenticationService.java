package com.mediscreen.gatewayservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DemoAuthenticationService {

    @Value("${spring.security.user.name}")
    private String demoUsername;

    @Value("${spring.security.user.password}")
    private String demoPassword;

    private final JwtService jwtService;

    public DemoAuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String authenticate(String username, String password) {
        if (demoUsername.equals(username) && demoPassword.equals(password)) {
            return jwtService.generateToken(username);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
