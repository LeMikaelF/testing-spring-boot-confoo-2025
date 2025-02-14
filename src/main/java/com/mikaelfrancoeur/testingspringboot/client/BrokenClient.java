package com.mikaelfrancoeur.testingspringboot.client;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class BrokenClient implements Client {

    private final RestTemplate restTemplate;

    @Override
    public String fetchAddress(String name) {
        URI uri = UriComponentsBuilder
                .fromPath("/address/{name}")
                .build(name);

        return restTemplate.exchange(uri, HttpMethod.GET, HttpEntity.EMPTY, String.class).getBody();
    }
}
