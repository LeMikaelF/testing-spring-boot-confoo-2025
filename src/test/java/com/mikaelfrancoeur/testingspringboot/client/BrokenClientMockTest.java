package com.mikaelfrancoeur.testingspringboot.client;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class BrokenClientMockTest implements WithAssertions {

    private static final String NAME = "name";
    private static final String ADDRESS = "the address";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BrokenClient brokenClient;

    @Test
    void poorTest() {
        //noinspection unchecked
        when(restTemplate.exchange(
                assertArg(uri -> assertThat(uri).hasToString("/address/name")),
                eq(HttpMethod.GET),
                eq(HttpEntity.EMPTY),
                any(Class.class))
        )
                .thenReturn(ResponseEntity.ok(ADDRESS));

        String address = brokenClient.fetchAddress(NAME);

        assertThat(address).isEqualTo(ADDRESS);
    }
}
