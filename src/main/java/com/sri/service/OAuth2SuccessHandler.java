package com.sri.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final AccessTokenService accessTokenService;

    public OAuth2SuccessHandler(
            OAuth2AuthorizedClientService authorizedClientService,
            AccessTokenService accessTokenService) {

        this.authorizedClientService = authorizedClientService;
        this.accessTokenService = accessTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oauth2Authentication = (OAuth2AuthenticationToken) authentication;

        String provider =  oauth2Authentication.getAuthorizedClientRegistrationId();

        String username = oauth2Authentication.getName();
        
        OAuth2AuthorizedClient authorizedClient =  authorizedClientService.loadAuthorizedClient(provider,username);

        if (authorizedClient == null) {
            throw new IllegalStateException("OAuth2 authorized client not found");
        }

        if (authorizedClient.getAccessToken() == null) {
            throw new IllegalStateException("Access token not found");
        }

        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        accessTokenService.saveToken(provider,accessToken);

    }
}