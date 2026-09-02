package com.sri.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sri.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class ProviderController {

    @Value("${oauth2.authorization-base-url}")
    private String authorizationBaseUrl;

    private final AuthService authService;


    @GetMapping("/providers")
    public ResponseEntity<?> getProviders() {

        log.info("Fetching available OAuth providers");

        return ResponseEntity.ok(
            Map.of(
                "providers", authService.getProviders()
            )
        );
    }


    @GetMapping("/login/{provider}")
    public void login(@PathVariable String provider,HttpServletResponse response) throws IOException {

        log.info("Login request received for provider: {}", provider);

        String providerId = authService.getLoginUrl(provider).get("provider");

        String redirectUrl = authorizationBaseUrl + providerId;

        log.info("Redirecting to OAuth2 authorization endpoint for provider: {}", providerId);

        response.sendRedirect(redirectUrl);
    }
}