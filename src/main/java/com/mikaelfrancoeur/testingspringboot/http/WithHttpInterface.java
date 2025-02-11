package com.mikaelfrancoeur.testingspringboot.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
class WithHttpInterface implements Client {

    private final HttpInterface http;

    WithHttpInterface(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl("http://localhost.com/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        this.http = factory.createClient(HttpInterface.class);
    }

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

}
