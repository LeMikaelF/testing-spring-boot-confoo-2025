package com.mikaelfrancoeur.testingspringboot.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FixedClient implements Client {

    private final RestTemplate restTemplate;

    @Override
    public String fetchAddress(String name) {
        return restTemplate.exchange("/address/{name}", HttpMethod.GET, HttpEntity.EMPTY, String.class, name).getBody();
    }
}
