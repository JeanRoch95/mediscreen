package com.mediscreen.gatewayservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
                        .pathMatchers(HttpMethod.POST).permitAll()
                        .pathMatchers("/logout").permitAll()
                        .and()
                        .httpBasic()
                        .and()
                        .logout()
                        .logoutUrl("/logout")
                        .logoutHandler(new WebSessionServerLogoutHandler())
                );

        return http.build();
    }
}
