package com.mikaelfrancoeur.testingspringboot.kafka;

import org.springframework.kafka.core.KafkaTemplate;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class MyKafkaProducer {
    private final KafkaTemplate<String, MyPayload> kafkaTemplate;

    @SneakyThrows
    void send(String topic, MyPayload message) {
        kafkaTemplate.send(topic, message).get();
    }
}
