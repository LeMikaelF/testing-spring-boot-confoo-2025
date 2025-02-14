package com.mikaelfrancoeur.testingspringboot.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class ClientConfig {

    @Bean
    RestTemplate brokenClientRestTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("https://www.google.com").build();
    }
}
