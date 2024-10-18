package org.okten.inventoryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.function.Supplier;

@Configuration
public class SecurityConfig {

    @Bean
    public Supplier<String> tokenProvider() {
        return () -> {
            JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            return authentication.getToken().getTokenValue();
        };
    }
}
