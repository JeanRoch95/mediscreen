package com.mediscreen.gatewayservice.controller;

import com.mediscreen.gatewayservice.model.Token;
import com.mediscreen.gatewayservice.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    private JwtService jwtService;

    public GatewayController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/validateToken")
    public boolean validateToken(@RequestBody Token token) {
        return jwtService.isTokenValid(token.getToken());
    }
}