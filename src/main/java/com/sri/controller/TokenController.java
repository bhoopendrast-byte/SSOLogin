package com.sri.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sri.service.AccessTokenService;

@RestController
@RequestMapping("/api")
public class TokenController {

    private final AccessTokenService accessTokenService;

    public TokenController(AccessTokenService accessTokenService) {

        this.accessTokenService = accessTokenService;
    }

    @GetMapping("/token/{provider}")
    public ResponseEntity<?> getToken(@PathVariable String provider) {

        String token = accessTokenService.getToken(provider);

        if (token == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                Map.of("accessToken", token)
        );
    }
}