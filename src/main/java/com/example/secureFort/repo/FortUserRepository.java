package com.example.secureFort.repo;


import com.example.secureFort.model.FortUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FortUserRepository
        extends JpaRepository<FortUser, Long> {

    Optional<FortUser> findByUsername(String username);
}


