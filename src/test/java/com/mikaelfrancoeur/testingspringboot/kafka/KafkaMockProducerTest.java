package com.mikaelfrancoeur.testingspringboot.kafka;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import lombok.Getter;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration(KafkaAutoConfiguration.class)
@SpringBootTest(classes = KafkaMockProducerTest.TestConfig.class)
public @interface KafkaMockProducerTest {

    @AliasFor(annotation = Import.class, attribute = "value")
    Class<?>[] classes();

    class TestConfig {
        @Bean
        BeanPostProcessor mockProducerPostProcessor() {
            return new BeanPostProcessor() {
                @Override
                public Object postProcessAfterInitialization(Object bean, String beanName) {
                    if (bean instanceof DefaultKafkaProducerFactory<?, ?> factory) {
                        return new MockKafkaProducerFactory<>(factory.getConfigurationProperties());
                    }
                    return bean;
                }
            };
        }

        @Bean
        MockProducer<?, ?> mockProducer(MockKafkaProducerFactory<?, ?> factory) {
            return factory.getMockProducer();
        }

        @Getter
        private static class MockKafkaProducerFactory<K, V> extends DefaultKafkaProducerFactory<K, V> {
            private final MockProducer<K, V> mockProducer;

            public MockKafkaProducerFactory(Map<String, Object> configs) {
                super(configs);
                ProducerConfig producerConfig = new ProducerConfig(configs);
                this.mockProducer = new MockProducer<>(true,
                        getKeySerializer(producerConfig),
                        getValueSerializer(configs, producerConfig));
            }

            @Override
            protected Producer<K, V> createRawProducer(Map<String, Object> rawConfigs) {
                return mockProducer;
            }

            @SuppressWarnings("unchecked")
            private static <K> Serializer<K> getKeySerializer(ProducerConfig producerConfig) {
                Serializer<K> keySerializer = producerConfig.getConfiguredInstance(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Serializer.class);
                keySerializer.configure(producerConfig.originals(), true);
                return keySerializer;
            }

            @SuppressWarnings("unchecked")
            private Serializer<V> getValueSerializer(Map<String, Object> rawConfigs, ProducerConfig producerConfig) {
                Serializer<V> valueSerializer = (Serializer<V>) producerConfig.getConfiguredInstance(
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                        Serializer.class);
                valueSerializer.configure(rawConfigs, false);
                return valueSerializer;
            }
        }
    }

}
