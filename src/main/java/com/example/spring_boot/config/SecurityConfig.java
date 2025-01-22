package com.example.spring_boot.config;

import com.example.spring_boot.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework. context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // DaoAuthenticationProvider that uses our CustomUserDetailsService
    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (if any)
                        .requestMatchers("/", "/register", "/login", "/home", "/css/**", "/js/**").permitAll()

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                // formLogin with default or custom login page
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll()  // let anyone see the login page
                )
                .logout(logout -> logout.permitAll());

        // If using H2 console, you might want to disable frameOptions
        // http.headers().frameOptions().disable();

        // Use our custom authProvider
        http.authenticationProvider(authProvider());

        return http.build();
    }
}