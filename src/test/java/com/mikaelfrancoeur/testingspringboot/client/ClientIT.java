package com.mikaelfrancoeur.testingspringboot.client;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({
        BrokenClient.class,
        ClientConfig.class,
        ClientIT.TestConfig.class,
})
class ClientIT implements WithAssertions {

    @Autowired
    private BrokenClient brokenClient;

    @Test
    @Disabled("should fail, for demo only")
    void test() {
        brokenClient.fetchAddress("anything");
    }

    static class TestConfig {
        @Bean
        RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder();
        }
    }
}
