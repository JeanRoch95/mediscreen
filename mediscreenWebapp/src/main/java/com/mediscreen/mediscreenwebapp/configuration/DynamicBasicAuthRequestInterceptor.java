package com.mediscreen.mediscreenwebapp.configuration;

import com.mediscreen.mediscreenwebapp.beans.CredentialBean;
import com.mediscreen.mediscreenwebapp.service.UserCredentialsService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class DynamicBasicAuthRequestInterceptor implements RequestInterceptor {

    private final UserCredentialsService userCredentialsService;

    @Autowired
    public DynamicBasicAuthRequestInterceptor(UserCredentialsService userCredentialsService) {
        this.userCredentialsService = userCredentialsService;
    }


    @Override
    public void apply(RequestTemplate template) {
        CredentialBean credentials = userCredentialsService.getCredentials();
        String authHeader = Base64.getEncoder().encodeToString((credentials.getUsername() + ":" + credentials.getPassword()).getBytes());
        template.header("Authorization", "Basic " + authHeader);
    }
}
