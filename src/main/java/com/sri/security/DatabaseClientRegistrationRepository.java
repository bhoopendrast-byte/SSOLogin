package com.sri.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

import com.sri.entity.OAuthProvider;
import com.sri.repository.OAuthProviderRepository;

@Component
public class DatabaseClientRegistrationRepository implements ClientRegistrationRepository, Iterable<ClientRegistration> {

    private final List<ClientRegistration> registrations = new ArrayList<>();

    public DatabaseClientRegistrationRepository(OAuthProviderRepository repository) {

        List<OAuthProvider> providers = repository.findAll();

        for (OAuthProvider provider : providers) {

            ClientRegistration registration = createRegistration(provider);

            registrations.add(registration);
        }
    }

    private ClientRegistration createRegistration(OAuthProvider provider) {

        ClientRegistration.Builder builder =
                ClientRegistration
                        .withRegistrationId(
                                provider.getProviderId()
                        );

        builder
            .clientId(provider.getClientId())
            .clientSecret(provider.getClientSecret())
            .clientAuthenticationMethod(
                    ClientAuthenticationMethod.CLIENT_SECRET_BASIC
            )
            .authorizationGrantType(
                    AuthorizationGrantType.AUTHORIZATION_CODE
            )
            .redirectUri(
                    "{baseUrl}/login/oauth2/code/{registrationId}"
            )
            .scope(
                    Arrays.stream(
                            provider.getScopes().split(",")
                    )
                    .map(String::trim)
                    .toArray(String[]::new)
            )
            .authorizationUri(
                    provider.getAuthorizationUri()
            )
            .tokenUri(
                    provider.getTokenUri()
            )
            .jwkSetUri(
                    provider.getJwkSetUri()
            );

        if (provider.getUserInfoUri() != null) {

            builder
                .userInfoUri(provider.getUserInfoUri())
                .userNameAttributeName(
                        provider.getUserNameAttribute()
                );
        }

        return builder.build();
    }

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {

        return registrations.stream()
                .filter(registration ->
                        registration.getRegistrationId()
                                .equals(registrationId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Iterator<ClientRegistration> iterator() {
        return registrations.iterator();
    }
}