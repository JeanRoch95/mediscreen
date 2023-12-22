package com.mediscreen.mediscreenwebapp.controller;

import com.mediscreen.mediscreenwebapp.model.UserCredential;
import com.mediscreen.mediscreenwebapp.proxies.LoginProxy;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final LoginProxy loginProxy;


    public LoginController(LoginProxy loginProxy) {
        this.loginProxy = loginProxy;
    }

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @PostMapping("/login")
    public String performLogin(@RequestParam String username, @RequestParam String password, HttpServletResponse request) {
        String jwtToken = loginProxy.login(new UserCredential(username, password));
        Cookie jwtCookie = new Cookie("jwtToken", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        request.addCookie(jwtCookie);
        return "redirect:/patient";
    }
}
