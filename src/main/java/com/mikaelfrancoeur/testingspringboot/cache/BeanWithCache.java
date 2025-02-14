package com.mikaelfrancoeur.testingspringboot.cache;

import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class BeanWithCache implements MessageFactory {

    @Override
    @Cacheable(cacheNames = "mycache", key = "#descriptor.message")
    public String getMessage(Descriptor descriptor) {
        return descriptor.message() + UUID.randomUUID();
    }

    public record Descriptor(String message) {
    }
}
