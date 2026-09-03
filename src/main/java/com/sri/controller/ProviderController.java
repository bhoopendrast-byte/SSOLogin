package com.sri.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sri.dto.TokenResponse;
import com.sri.service.AccessTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class ProviderController {

    private final AccessTokenService accessTokenService;

    @GetMapping("/login/{provider}")
    public ResponseEntity<TokenResponse> login(@PathVariable String provider) {

        log.info("Login request received for provider: {}", provider);

        String accessToken = accessTokenService.getToken(provider);

        if (accessToken == null) {
            throw new IllegalArgumentException("Access token not found for provider: " + provider);
        }

        log.info("Returning access token for provider: {}", provider);

        return ResponseEntity.ok(new TokenResponse(accessToken));
    }
}