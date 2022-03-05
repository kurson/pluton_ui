package com.kurek.antoni.pluton.ui;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public record UserInfo(String email, String name) {
    public UserInfo(OAuth2AuthenticationToken token) {
        this(token.getPrincipal().getAttribute("email"), token.getPrincipal().getAttribute("name"));
    }
}
