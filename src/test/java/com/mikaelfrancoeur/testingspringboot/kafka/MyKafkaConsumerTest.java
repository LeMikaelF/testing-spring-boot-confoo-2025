package com.mikaelfrancoeur.testingspringboot.kafka;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import lombok.SneakyThrows;

@EmbeddedKafka
@ImportAutoConfiguration(KafkaAutoConfiguration.class)
@SpringBootTest(classes = MyKafkaConsumer.class)
class MyKafkaConsumerTest implements WithAssertions {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockitoSpyBean
    private MyKafkaConsumer kafkaConsumer;

    @BeforeEach
    void beforeEach() {
        registry.getAllListenerContainers().forEach(container -> {
            ContainerTestUtils.waitForAssignment(container, 2 * embeddedKafkaBroker.getPartitionsPerTopic()); // 2 topics
        });

        doNothing().when(kafkaConsumer).listen(any(), any());
    }

    @Test
    @SneakyThrows
    void test() {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("testtopic2", "my message");
        producerRecord.headers().add("X-Correlation-Id", "mikael".getBytes(StandardCharsets.UTF_8));
        kafkaTemplate.send(producerRecord).get();

        Mockito.verify(kafkaConsumer, timeout(5_000)).listen("my message", "mikael");
    }

}
