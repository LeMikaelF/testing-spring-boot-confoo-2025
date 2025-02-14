package com.mikaelfrancoeur.testingspringboot.client;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@Import(ClientConfig.class)
@RestClientTest(value = {
        BrokenClient.class,
        FixedClient.class,
})
public class ClientBrokenIT implements WithAssertions {

    private static final String ADDRESS = "the address";
    private static final String NAME = "name";

    @Autowired
    private BrokenClient brokenClient;
    @Autowired
    private FixedClient fixedClient;


    @Autowired
    private MockRestServiceServer server;

    @Test
    void brokenClient() {
        server.expect(requestTo("/address/name")).andRespond(withSuccess(ADDRESS, MediaType.TEXT_HTML));

        String address = brokenClient.fetchAddress(NAME);

        assertThat(address).isEqualTo(ADDRESS);
    }

    @Test
    void fixedClient() {
        server.expect(requestTo("/address/name")).andRespond(withSuccess(ADDRESS, MediaType.TEXT_HTML));

        String address = fixedClient.fetchAddress(NAME);

        assertThat(address).isEqualTo(ADDRESS);
    }
}
