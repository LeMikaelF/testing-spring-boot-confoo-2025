package com.mikaelfrancoeur.testingspringboot.client;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

public class SimpleClientTest implements WithAssertions {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
    private final WithRestTemplate client = new WithRestTemplate(restTemplate);

    @Test
    void clientFetchesAddress() {
        server.expect(anything()).andRespond(withSuccess("""
                {
                    "address": "North Pole"
                }
                """, MediaType.APPLICATION_JSON));

        String address = client.fetchAddress("Santa Claus");

        assertThat(address).isEqualTo("North Pole");
    }
}
