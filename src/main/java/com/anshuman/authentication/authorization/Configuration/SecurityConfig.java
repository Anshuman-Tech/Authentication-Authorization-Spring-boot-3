package com.anshuman.authentication.authorization.Configuration;

import com.anshuman.authentication.authorization.Security.JwtAuthenticationEntryPoint;
import com.anshuman.authentication.authorization.Security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
/*
The @EnableGlobalMethodSecurity() has been deprecated, but we are able to use it.
It has @PreAuthorize() and @PostAuthorize().
We can enable the role-based access in the antMatchers by using the hasRole() for single role, and hasAnyRole() for multiple roles.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

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
                    auth.requestMatchers("/api/user/add","/api/home/","/api/user/loggedIn","/api/user/login").permitAll()
//                            .requestMatchers("/api/user/get").hasRole("Admin_User")
                            .anyRequest().authenticated();
                })
                .exceptionHandling(ex->ex.authenticationEntryPoint(point))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
