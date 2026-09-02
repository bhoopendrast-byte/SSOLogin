package com.sri.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {

    private final Map<String, String> tokens = new ConcurrentHashMap<>();

    public void saveToken(String provider, String accessToken) {
        tokens.put(provider, accessToken);
    }

    public String getToken(String provider) {
        return tokens.get(provider);
    }
}