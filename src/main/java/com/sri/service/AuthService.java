package com.sri.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sri.entity.OAuthProvider;
import com.sri.repository.OAuthProviderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private OAuthProviderRepository oauthProviderRepository;


    @Cacheable("oauthProviders")
    public List<Map<String, String>> getProviders() {

        log.info("Loading OAuth providers from database");

        List<Map<String, String>> providers =
                oauthProviderRepository.findAll()
                    .stream()
                    .map(provider -> Map.of(
                        "name", provider.getProviderName(),
                        "id", provider.getProviderId(),
                        "loginUrl", provider.getLoginUrl()
                    ))
                    .toList();

        log.info("Successfully loaded {} OAuth providers", providers.size());

        return providers;
    }


    @Cacheable(value = "oauthProvider", key = "#provider")
    public Map<String, String> getLoginUrl(String provider) {

        log.info("Searching for OAuth provider: {}", provider);

        OAuthProvider oauthProvider =
                oauthProviderRepository
                    .findByProviderId(provider)
                    .orElseThrow(() -> {
                        log.warn("Unsupported OAuth provider requested: {}", provider);

                        return new IllegalArgumentException(
                            "Unsupported authentication provider: " + provider
                        );
                    });

        log.info("OAuth provider found: {}", oauthProvider.getProviderId());

        return Map.of(
            "provider", oauthProvider.getProviderId(),
            "loginUrl", oauthProvider.getLoginUrl()
        );
    }
}