package com.mikaelfrancoeur.testingspringboot.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class BrokenClientConfig {

    @Bean
    RestTemplate brokenClientRestTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("www.google.com").build();
    }
}
