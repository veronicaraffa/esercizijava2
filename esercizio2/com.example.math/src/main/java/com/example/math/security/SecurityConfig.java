package com.example.math.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/math/**").permitAll()        // aperto
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // SOLO ADMIN
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults()); // Basic Auth attivo
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        var admin = User.withDefaultPasswordEncoder()
                .username("admin").password("admin").roles("ADMIN").build();
        var user = User.withDefaultPasswordEncoder()
                .username("user").password("user").roles("CUSTOMER").build();
        return new InMemoryUserDetailsManager(admin, user);
    }
}
