package com.mikaelfrancoeur.testingspringboot.restclient;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@SuppressWarnings("SameParameterValue")
class Client {
    private final RestClient restClient;

    Client(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    String fetchAddress(String name) {
        return Objects.requireNonNull(restClient.get()
                        .uri("http://localhost.com/{name}", name)
                        .retrieve()
                        .body(Response.class))
                .address();
    }

    private record Response(String address) {
    }
}
