package com.sri.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oauth_provider")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthProvider {

    @Id
    @SequenceGenerator(name = "gen1",sequenceName = "s1",initialValue = 100,allocationSize = 1)
    @GeneratedValue(generator = "gen1", strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "provider_name")
    private String providerName;


    @Column(name = "provider_id", unique = true)
    private String providerId;


    @Column(name = "login_url")
    private String loginUrl;


    @Column(name = "client_id")
    private String clientId;


    @Column(name = "client_secret")
    private String clientSecret;


    @Column(name = "scopes")
    private String scopes;


    @Column(name = "authorization_uri")
    private String authorizationUri;


    @Column(name = "token_uri")
    private String tokenUri;


    @Column(name = "user_info_uri")
    private String userInfoUri;


    @Column(name = "user_name_attribute")
    private String userNameAttribute;


    @Column(name = "jwk_set_uri")
    private String jwkSetUri;

    
    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;


    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;


    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;


    @Column(name = "updated_by",nullable = false)
    private String updatedBy;


    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        createdBy = "SYSTEM";
        updatedBy = "SYSTEM";
    }


    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();

        updatedBy = "SYSTEM";
    }
}