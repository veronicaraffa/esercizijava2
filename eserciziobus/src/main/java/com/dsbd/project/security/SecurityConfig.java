package com.dsbd.project.security;

import com.dsbd.project.service.ProjectUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final ProjectUserService userDetailsService;

    public SecurityConfig(ProjectUserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    // Definiamo il bean AuthTokenFilter qui
    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter(); // se serve AuthenticationManager, puoi passarla qui tramite costruttore
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(auth -> auth
                        // Pubblici
                        .requestMatchers(
                                new AntPathRequestMatcher("/user/register", "POST"),
                                new AntPathRequestMatcher("/user/login", "POST"),
                                new AntPathRequestMatcher("/trips", "GET")
                        ).permitAll()
                        // Solo ADMIN
                        .requestMatchers(new AntPathRequestMatcher("/trips", "POST")).hasAuthority("ADMIN")
                        // Autenticati
                        .requestMatchers(
                                new AntPathRequestMatcher("/trips/**/buy", "POST"),
                                new AntPathRequestMatcher("/user/me"),
                                new AntPathRequestMatcher("/user/me/credit/toup", "PATCH")
                        ).authenticated()
                        // Tutto il resto autenticato
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
