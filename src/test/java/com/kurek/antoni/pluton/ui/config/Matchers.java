package com.kurek.antoni.pluton.ui.config;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;

public class Matchers {
    public static HttpRequest getOwnedPortfoliosRequest(String email) {
        return argThat((HttpRequest r) -> r.uri()
                                           .toString()
                                           .contains("/portfolio/owned/" + email)
                && r.method().equals("GET"));
    }

    public static HttpResponse.BodyHandler<String> responseHandler() {
        return eq(HttpResponse.BodyHandlers.ofString());
    }
}
