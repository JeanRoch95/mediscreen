package com.mediscreen.gatewayservice.service;

import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

@Service
public class CookieService {

    public void deleteCookies(ServerHttpRequest request, ServerHttpResponse response) {
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();

        for (Map.Entry<String, List<HttpCookie>> cookie : cookies.entrySet()) {
            for (HttpCookie cookieToBeDeleted : cookie.getValue()) {
                response.addCookie(ResponseCookie.from(cookieToBeDeleted.getName(), cookieToBeDeleted.getValue())
                        .maxAge(0).build());
            }
        }


    }
}
