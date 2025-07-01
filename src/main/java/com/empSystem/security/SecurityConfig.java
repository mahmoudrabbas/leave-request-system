package com.empSystem.security;

import com.empSystem.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(
                                    "/auth/**"
                            ).permitAll() // localhost:8080/auth -> permit all
                            .requestMatchers("/departments/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/employees").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/employees/{id}").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.PUT, "/employees/{id}").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.DELETE, "/employees/{id}").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.POST, "/employees").hasAnyRole("USER", "ADMIN")
                            .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.GET, "/leave-request").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/leave-request").hasAnyRole("ADMIN", "USER`")
                            .requestMatchers(HttpMethod.GET, "/leave-request/{id}").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.GET, "/leave-request/emp/{id}").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.DELETE, "/leave-request/{id}").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.PUT, "/leave-request/{id}").hasAnyRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .authenticationManager(authenticationManager(http));

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder())
//                .and().build();
//    }
}
