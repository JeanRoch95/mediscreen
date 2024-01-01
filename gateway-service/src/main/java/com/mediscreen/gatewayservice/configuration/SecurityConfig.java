package com.mediscreen.gatewayservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.WebSession;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeExchange((requests) -> requests
                        .pathMatchers("/login").permitAll()
                        .pathMatchers("/api/**").authenticated()
                        .pathMatchers(HttpMethod.PUT).permitAll()
                        .pathMatchers(HttpMethod.POST, "/logout").permitAll()
                        .and()
                        .httpBasic()
                        .and()
                        .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((exchange, authentication) -> {
                            ServerHttpResponse response = exchange.getExchange().getResponse();
                            response.setStatusCode(HttpStatus.FOUND);
                            response.getHeaders().setLocation(URI.create("http://localhost:9003/login"));
                            response.getCookies().remove("JSESSIONID");
                            response.getHeaders().add("Set-Cookie", "JSESSIONID=; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT; HttpOnly;");
                            return exchange.getExchange().getSession().flatMap(WebSession::invalidate);
                        })
                );

        return http.build();
    }
}
