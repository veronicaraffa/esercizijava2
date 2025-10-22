package com.dsbd.project.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Trova un utente tramite email
    Optional<User> findByEmail(String email);

    // Controlla se un utente con la stessa email esiste
    boolean existsByEmail(String email);
}
