package com.mikaelfrancoeur.testingspringboot.http;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class WithHttpInterface implements Client {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final HttpInterface http;

    @Override
    public String fetchAddress(String name) {
        return http.get().address();
    }

    private interface HttpInterface {
        @GetExchange
        Response get();
    }

    private record Response(String address) {
    }

    @Bean
    private static HttpInterface httpInterface(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl("https://api.github.com/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(HttpInterface.class);
    }
}
