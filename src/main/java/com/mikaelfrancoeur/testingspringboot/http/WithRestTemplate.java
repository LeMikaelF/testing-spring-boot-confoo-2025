package com.mikaelfrancoeur.testingspringboot.http;

import java.util.Objects;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class WithRestTemplate implements Client {

    private final RestTemplate restTemplate;

    WithRestTemplate(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public String fetchAddress(String name) {
        record Response(String address) {
        }
        return Objects.requireNonNull(restTemplate.getForObject("http://localhost.com/", Response.class))
                .address();
    }

}
