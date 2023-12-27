package com.mediscreen.gatewayservice.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @GetMapping("/logout")
    public void logout() {
        WebSessionServerLogoutHandler webSessionServerLogoutHandler = new WebSessionServerLogoutHandler();
        SecurityContextHolder.clearContext();
    }
}
