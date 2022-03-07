package com.kurek.antoni.pluton.ui.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class TestUser implements OAuth2User {
    private final String email;
    private final String name;

    public TestUser(String name) {
        this.email = "user@domain.host";
        this.name = name;
    }

    public TestUser() {
        this.email = "user@domain.host";
        this.name = "John Doe";
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of("email", email, "name", name);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return "principal name?";
    }
}
