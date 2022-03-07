package com.kurek.antoni.pluton.ui.homeView;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class HomeHandler {
    private final HttpClient httpClient;

    @Value("${fake-service-discovery.portfolio-storage}")
    private String portfolioStorageUri;

    public void getOwnedPortfolios(String email) throws URISyntaxException, IOException, InterruptedException {
        httpClient.send(HttpRequest.newBuilder()
                                   .uri(new URI("http://" + portfolioStorageUri + "/ownedPortfolios/" + email))
                                   .GET()
                                   .build(),
                        HttpResponse.BodyHandlers.ofString());
    }
}
