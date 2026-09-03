package com.sri.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.entity.OAuthProvider;

public interface OAuthProviderRepository extends JpaRepository<OAuthProvider, Long> {
	Optional<OAuthProvider> findByProviderId(String providerId);
}
