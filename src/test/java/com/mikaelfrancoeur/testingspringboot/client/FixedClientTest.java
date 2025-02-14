package com.mikaelfrancoeur.testingspringboot.client;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@Import({ FixedClient.class, ClientConfig.class, FixedClientTest.TestConfig.class })
class FixedClientTest implements WithAssertions {

    private static final String ADDRESS = "the address";
    private static final String NAME = "name";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FixedClient fixedClient;

    @Test
    void betterTest() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(requestTo("https://www.google.com/address/name")).andRespond(withSuccess(ADDRESS, MediaType.TEXT_HTML));

        String address = fixedClient.fetchAddress(NAME);

        assertThat(address).isEqualTo(ADDRESS);
    }

    static class TestConfig {
        @Bean
        RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder();
        }
    }
}
