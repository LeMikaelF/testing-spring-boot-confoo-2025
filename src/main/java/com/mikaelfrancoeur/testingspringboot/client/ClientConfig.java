package com.mikaelfrancoeur.testingspringboot.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import(RestTemplate.class)
class ClientConfig {
}
