package com.mikaelfrancoeur.testingspringboot.http;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@SuppressWarnings("SameParameterValue")
class WithRestClient implements Client{
    private final RestClient restClient;

    WithRestClient(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    @Override
    public String fetchAddress(String name) {
        return Objects.requireNonNull(restClient.get()
                        .uri("http://localhost.com/{name}", name)
                        .retrieve()
                        .body(Response.class))
                .address();
    }

    private record Response(String address) {
    }
}
