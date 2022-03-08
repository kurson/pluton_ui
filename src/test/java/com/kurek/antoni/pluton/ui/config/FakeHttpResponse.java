package com.kurek.antoni.pluton.ui.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.SneakyThrows;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Builder
public class FakeHttpResponse implements HttpResponse<String> {
    private final ObjectMapper mapper = new ObjectMapper();
    private int statusCode;
    private HttpRequest request;
    private HttpHeaders headers;
    private Object body;
    private URI uri;
    private HttpClient.Version version;


    @Override
    public int statusCode() {
        return statusCode;
    }

    @Override
    public HttpRequest request() {
        return request;
    }

    @Override
    public Optional<HttpResponse<String>> previousResponse() {
        return Optional.empty();
    }

    @Override
    public HttpHeaders headers() {
        return headers;
    }

    @SneakyThrows
    @Override
    public String body() {
        return mapper.writeValueAsString(body);
    }

    @Override
    public Optional<SSLSession> sslSession() {
        return Optional.empty();
    }

    @Override
    public URI uri() {
        return uri;
    }

    @Override
    public HttpClient.Version version() {
        return version;
    }
}
