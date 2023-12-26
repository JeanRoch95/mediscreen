package com.mediscreen.mediscreenwebapp.configuration;

import com.mediscreen.mediscreenwebapp.service.UserCredentialsService;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor(UserCredentialsService userCredentialsService) {
        return new DynamicBasicAuthRequestInterceptor(userCredentialsService);
    }
}
