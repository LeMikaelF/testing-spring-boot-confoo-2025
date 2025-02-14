package com.mikaelfrancoeur.testingspringboot.kafka;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import lombok.SneakyThrows;

@KafkaMockProducerTest(classes = MyKafkaProducer.class)
class MyKafkaProducerMockTest implements WithAssertions {

    @Autowired
    MyKafkaProducer myKafkaProducer;

    @Autowired
    private MockProducer<?, ?> mockProducer;

    @BeforeEach
    void beforeEach() {
        mockProducer.clear();
    }

    @Test
    void sendsMessage() {
        MyPayload message = new MyPayload("message", new Date(), UUID.randomUUID(), new MyPayload.Email("mikael@francoeur.com"));
        myKafkaProducer.send("the-topic", message);

        assertThat(mockProducer.history())
                .singleElement()
                .extracting(ProducerRecord::topic, ProducerRecord::key, ProducerRecord::value)
                .containsExactly("the-topic", null, message);
    }

    @Test
    @SneakyThrows
    void registersCorrectSchema() {
        MyPayload message = new MyPayload("message", new Date(), UUID.randomUUID(), new MyPayload.Email("mikael@francoeur.com"));
        myKafkaProducer.send("the-topic", message);

        SchemaRegistryClient schemaRegistryClient = MockSchemaRegistry.getClientForScope("my-test-scope");
        String schema = schemaRegistryClient.getLatestSchemaMetadata("the-topic-value").getSchema();
        // schema:
        // {"type":"record","name":"MyPayload","namespace":"com.mikaelfrancoeur.testerspringboot.kafka","fields":[{"name":"date","type":"string"},{"name":"email","type":"string"},{"name":"theMessage","type":"string"}]}

        DocumentContext documentContext = JsonPath.parse(schema);
        assertThat(documentContext.read("$.fields[?(@.name == 'theMessage' && @.type == 'string')]", List.class)).hasSize(1);
        assertThat(documentContext.read("$.fields[?(@.name == 'date' && @.type == 'string')]", List.class)).hasSize(1);
    }
}
