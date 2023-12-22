package com.mediscreen.mediscreenwebapp.configuration;

import com.mediscreen.mediscreenwebapp.controller.LoginController;
import feign.RequestInterceptor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    private static final Logger logger = LoggerFactory.getLogger(FeignConfig.class);

    @Bean
    public RequestInterceptor jwtRequestInterceptor() {
        return template -> {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (request != null) {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("jwtToken".equals(cookie.getName())) {
                            String jwtToken = cookie.getValue();
                            logger.info("JWT in FeignConfig: {}", jwtToken);
                            template.header("Authorization", "Bearer " + jwtToken);
                        }
                    }
                }
            }
        };
    }
}
