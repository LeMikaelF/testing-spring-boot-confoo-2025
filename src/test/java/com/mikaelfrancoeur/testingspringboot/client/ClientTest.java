package com.mikaelfrancoeur.testingspringboot.client;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(WithRestTemplate.class) @AutoConfigureWebClient(registerRestTemplate = true)
//@RestClientTest(WithHttpInterface.class)
//@RestClientTest(WithRestClient.class)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class ClientTest implements WithAssertions {

    @Autowired
    private Client client;

    @Autowired
    private MockRestServiceServer server;

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
