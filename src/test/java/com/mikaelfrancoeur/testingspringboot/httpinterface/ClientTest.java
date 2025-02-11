package com.mikaelfrancoeur.testingspringboot.httpinterface;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(Client.class)
class ClientTest {

    @Autowired
    Client client;

    @Autowired
    MockRestServiceServer server;

    @Test
    void test() {
        server.expect(anything()).andRespond(withSuccess());

        client.fetchSomething();
    }
}
