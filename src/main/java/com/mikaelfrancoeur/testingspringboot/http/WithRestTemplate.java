package com.mikaelfrancoeur.testingspringboot.http;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class WithRestTemplate implements Client {

    private final RestTemplate restTemplate;

    @Override
    public String fetchAddress(String name) {
        record Response(String address) {
        }
        return Objects.requireNonNull(restTemplate.getForObject("http://localhost.com/", Response.class))
                .address();
    }

}
