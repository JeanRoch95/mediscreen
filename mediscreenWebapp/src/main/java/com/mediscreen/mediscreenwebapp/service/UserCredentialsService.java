package com.mediscreen.mediscreenwebapp.service;


import com.mediscreen.mediscreenwebapp.beans.CredentialBean;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialsService {

    private final HttpSession httpSession;

    private static final String CREDENTIALS_SESSION_KEY = "userCredentials";

    public UserCredentialsService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void saveCredentials(CredentialBean credentials) {
        httpSession.setAttribute(CREDENTIALS_SESSION_KEY, credentials);
    }

    public CredentialBean getCredentials() {
        return (CredentialBean) httpSession.getAttribute(CREDENTIALS_SESSION_KEY);
    }
}
