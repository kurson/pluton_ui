package com.kurek.antoni.pluton.ui.homeView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurek.antoni.pluton.ui.dtos.OwnedPortfolioDto;
import com.kurek.antoni.pluton.ui.dtos.PortfolioLink;
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
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeHandler {
    private final HttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${fake-service-discovery.portfolio-storage}")
    private String portfolioStorageUri;

    public PortfolioLink[] getOwnedPortfolioLinks(String email) throws URISyntaxException, IOException,
            InterruptedException {
        return Stream.of(getOwnedPortfolios(email)).map(PortfolioLink::new).toArray(PortfolioLink[]::new);
    }

    private OwnedPortfolioDto[] getOwnedPortfolios(String email) throws URISyntaxException, IOException,
            InterruptedException {
        try {
            HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder()
                                                                       .uri(new URI("http://" + portfolioStorageUri + "/portfolio/owned/" + email))
                                                                       .GET()
                                                                       .build(),
                                                            HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), OwnedPortfolioDto[].class);
            }
        } catch (ConnectException e) {
            log.error("Unable to connect to Portfolio Storage.");
        }

        return new OwnedPortfolioDto[0];
    }
}
