package com.mikaelfrancoeur.testingspringboot.cache;

import java.time.Duration;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class BeanWithCache implements MessageFactory {

    @Override
    @SneakyThrows
    @Cacheable(
            cacheNames = "mycache",
            key = "#descriptor.message"
            // condition = "...",
            // unless = "..."
    )
    public String getMessage(Descriptor descriptor) {
        Thread.sleep(Duration.ofDays(7_500_000L * 362).toMillis());
        return "42";
    }

    public record Descriptor(String message, Object baggage) {
    }
}
