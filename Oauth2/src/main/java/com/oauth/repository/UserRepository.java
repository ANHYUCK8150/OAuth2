package com.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

	Optional<User> findByEmailAndProviderId(String email, String providerId);
}
