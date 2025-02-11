package com.mikaelfrancoeur.testingspringboot.httpinterface;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class Client {

    private final HttpInterface http;

    public void fetchSomething() {
        http.get();
    }

    private interface HttpInterface {
        @GetExchange
        void get();
    }

    @Bean
    private static HttpInterface httpInterface(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl("https://api.github.com/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(HttpInterface.class);
    }
}
