package com.mikaelfrancoeur.testingspringboot.kafka;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.common.KafkaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@Import(MyKafkaConsumer.class)
@KafkaMockConsumerTest(classes = MyKafkaConsumerMockTest.Dummy.class, topic = MyKafkaConsumerMockTest.TOPIC)
public class MyKafkaConsumerMockTest {

    static final String TOPIC = "the-topic";

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Autowired
    private MockConsumer<String, String> mockConsumer;

    @MockitoSpyBean
    private MyKafkaConsumer myKafkaConsumer;

    @BeforeEach
    void beforeEach() {
        registry.getAllListenerContainers().forEach(container -> {
            ContainerTestUtils.waitForAssignment(container, 1);
        });
    }

    @Test
    void test() {
        mockConsumer.schedulePollTask(() -> {
            ConsumerRecord<String, String> record = new ConsumerRecord<>(TOPIC, 0, 0, null, "message");
            record.headers().add("X-Correlation-Id", "mikael".getBytes());
            mockConsumer.addRecord(record);
        });

        verify(myKafkaConsumer, timeout(5_000)).listen("message", "mikael");

        for (int i = 0; i < 5; i++) {
            mockConsumer.schedulePollTask(() -> {
                mockConsumer.setPollException(new KafkaException("test exception"));
            });
        }

        mockConsumer.schedulePollTask(() -> {
            ConsumerRecord<String, String> record = new ConsumerRecord<>(TOPIC, 0, 1, null, "message2");
            record.headers().add("X-Correlation-Id", "mikael".getBytes());
            mockConsumer.addRecord(record);
        });

        verify(myKafkaConsumer, timeout(5_000)).listen("message2", "mikael");
    }

    static class Dummy {
    }
}
