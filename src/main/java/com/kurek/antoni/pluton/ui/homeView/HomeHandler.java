package com.kurek.antoni.pluton.ui.homeView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurek.antoni.pluton.ui.dtos.OwnedPortfolioDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeHandler {
    private final HttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();
    @Value("${fake-service-discovery.portfolio-storage}")
    private String portfolioStorageUri;

    public OwnedPortfolioDto[] getOwnedPortfolios(String email) throws URISyntaxException, IOException,
            InterruptedException {
        try {
            HttpResponse<String> ownedPortfoliosResponse =
                    httpClient.send(HttpRequest
                                            .newBuilder()
                                            .uri(new URI("http://" + portfolioStorageUri + "/ownedPortfolios/" + email))
                                            .GET()
                                            .build(),
                                    HttpResponse.BodyHandlers.ofString());
            if (ownedPortfoliosResponse.statusCode() == 200) {
                return mapper.readValue(ownedPortfoliosResponse.body(), OwnedPortfolioDto[].class);
            }
        } catch (ConnectException e) {
            log.error("Unable to connect to Portfolio Storage.");
        }

        return new OwnedPortfolioDto[0];
    }
}
