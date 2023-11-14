package com.anshuman.authentication.authorization.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->{
                    csrf.disable();
                })
                .cors(cors->{
                    cors.disable();
                })
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/api/user/add").permitAll().anyRequest().authenticated();
                });

        return http.build();
    }
}