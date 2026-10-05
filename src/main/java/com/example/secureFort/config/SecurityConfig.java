
package com.example.secureFort.config;

import com.example.secureFort.repo.FortUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private FortUserRepository userRepository;


    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        return username ->

            userRepository
                .findByUsername(username)
                .orElseThrow(
                    () -> new UsernameNotFoundException(
                        "User '" + username + "' not found"
                    )
                );
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // CSRF protection
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(PathRequest.toH2Console())
            )

            // Authorization rules
            .authorizeHttpRequests(auth -> auth

                // H2 Console
                .requestMatchers(PathRequest.toH2Console())
                .permitAll()

                // Public pages
                .requestMatchers(
                    "/marketplace",
                    "/login",
                    "/register",
                    "/error"
                )
                .permitAll()

                // Admin only
                .requestMatchers("/treasury")
                .hasRole("ADMIN")

                // Logged-in users
                .requestMatchers(
                    "/entrance",
                    "/csrf-demo",
                    "/csrf-demo/purchase"
                )
                .authenticated()

                // Everything else is denied
                .anyRequest()
                .denyAll()
            )

            // Form Login
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/entrance", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )

            // Logout
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )

            // H2 Console runs inside a frame
            .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            );

        return http.build();
    }
}

