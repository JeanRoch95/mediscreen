package com.mediscreen.gatewayservice.configuration;

import com.mediscreen.gatewayservice.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class JwtFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtService jwtService;

    /**
     * Checks if the request has a valid JWT token in the Authorization header.
     * If not, the request is rejected with a 401 status
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route != null) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null) {
            List<HttpCookie> cookies = exchange.getRequest().getCookies().get("jwtToken");
            if (cookies != null && !cookies.isEmpty()) {
                String token = cookies.get(0).getValue();
                if (jwtService.isTokenValid(token)) {
                    return chain.filter(exchange);
                }
            }
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
