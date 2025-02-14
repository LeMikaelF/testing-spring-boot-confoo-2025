package com.mikaelfrancoeur.testingspringboot.client;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(components = MyClient.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class MyClientTest implements WithAssertions {

    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private MyClient myClient;

    @Test
    void searchDevices_200response() {
        server.expect(requestTo("/devices"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json("[{\"name\":\"name\"},{\"id\":\"deviceId\"}]"))
                .andRespond(withSuccess("[{\"id\":\"deviceId\",\"name\":\"name\"}]", MediaType.APPLICATION_JSON));

        List<DeviceFilter> deviceFilters = List.of(new DeviceFilter.Name("name"), new DeviceFilter.Id("deviceId"));
        List<Device> devices = myClient.searchDevices(deviceFilters);

        server.verify();
        assertThat(devices).containsExactly(new Device("deviceId", "name"));
    }

}
