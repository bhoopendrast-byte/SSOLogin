package com.sri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProviderResponse {

    private String providerId;
    private String providerName;
    private String loginUrl;
}