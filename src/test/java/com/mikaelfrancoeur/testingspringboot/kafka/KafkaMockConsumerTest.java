package com.mikaelfrancoeur.testingspringboot.kafka;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@KafkaMockConsumerTest.MockConsumerProperties
@ImportAutoConfiguration({ KafkaAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class })
@SpringBootTest(classes = { TestConfig.class, TestConfig.Dummy.class })
@Import(TestConfig.class)
public @interface KafkaMockConsumerTest {

    @AliasFor(annotation = MockConsumerProperties.class, attribute = "topic")
    String topic();

    @AliasFor(annotation = Import.class, attribute = "value")
    Class<?>[] classes();

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @PropertyMapping("spring.kafka.mock")
    @interface MockConsumerProperties {
        String topic() default "";
    }
}

@TestConfiguration
class TestConfig {
    static class Dummy {
    }

    @Value("${spring.kafka.mock.topic}")
    private String topic;

    @Bean
    MockConsumer<String, String> mockConsumer() {
        MockConsumer<String, String> mockConsumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        TopicPartition topicPartition = new TopicPartition(topic, 0);
        Map<TopicPartition, Long> beginningOffsets = Map.of(topicPartition, 0L);
        mockConsumer.updateBeginningOffsets(beginningOffsets);

        mockConsumer.schedulePollTask(() -> {
            mockConsumer.rebalance(Collections.singletonList(topicPartition));
        });

        return mockConsumer;
    }

    @Bean
    BeanPostProcessor myKafkaPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof DefaultKafkaConsumerFactory<?, ?> factory) {
                    return new DefaultKafkaConsumerFactory<String, String>(factory.getConfigurationProperties()) {
                        @Override
                        protected Consumer<String, String> createKafkaConsumer(Map<String, Object> configProps) {
                            return mockConsumer();
                        }
                    };
                }
                return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
            }
        };
    }
}
