package com.mikaelfrancoeur.testingspringboot.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyKafkaConsumer {

    // language=SpEL
    @KafkaListener(topics = "#{@environment.getProperty('kafka.consumer.topics').split(',')}")
    public void listen(@Payload String message, @Header("X-Correlation-Id") String correlationId) {
        log.info("Received message with correlation ID {}: {}", correlationId, message);
    }
}
