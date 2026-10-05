package com.example.secureFort.config;


import com.example.secureFort.model.FortUser;
import com.example.secureFort.repo.FortUserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedUsers(
            FortUserRepository repository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (repository
                    .findByUsername("customer")
                    .isEmpty()) {

                repository.save(
                    new FortUser(
                        "customer",
                        passwordEncoder.encode(
                            "Customer@123"
                        ),
                        "CUSTOMER"
                    )
                );
            }

            if (repository
                    .findByUsername("admin")
                    .isEmpty()) {

                repository.save(
                    new FortUser(
                        "admin",
                        passwordEncoder.encode(
                            "Admin@123"
                        ),
                        "ADMIN"
                    )
                );
            }
        };
    }
}
