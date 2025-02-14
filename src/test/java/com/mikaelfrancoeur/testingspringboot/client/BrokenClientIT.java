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

@Import(BrokenClientConfig.class)
@RestClientTest(BrokenClient.class)
public class BrokenClientIT implements WithAssertions {

    private static final String ADDRESS = "the address";
    private static final String NAME = "name";
    @Autowired
    private BrokenClient brokenClient;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void test() {
        server.expect(requestTo("www.google.com/address/name")).andRespond(withSuccess(ADDRESS, MediaType.TEXT_HTML));

        String address = brokenClient.fetchAddress(NAME);

        assertThat(address).isEqualTo(ADDRESS);
    }
}
