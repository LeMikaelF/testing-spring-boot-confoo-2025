package com.mikaelfrancoeur.testingspringboot.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class MyClient {

    private static final ParameterizedTypeReference<List<Device>> DEVICE_LIST_TYPE = new ParameterizedTypeReference<>() {
    };

    private final RestTemplate restTemplate;

    List<Device> searchDevices(List<DeviceFilter> filters) {
        return restTemplate.exchange("/devices", HttpMethod.POST, new HttpEntity<>(filters), DEVICE_LIST_TYPE).getBody();
    }

}

record Device(String id, String name) {
}

sealed interface DeviceFilter permits DeviceFilter.Name, DeviceFilter.Id {
    record Name(String name) implements DeviceFilter {
    }

    record Id(String id) implements DeviceFilter {
    }
}
