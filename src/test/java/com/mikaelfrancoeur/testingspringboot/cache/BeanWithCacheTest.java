package com.mikaelfrancoeur.testingspringboot.cache;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.util.AopTestUtils;

@SpringBootTest
@ImportAutoConfiguration(CacheAutoConfiguration.class)
public class BeanWithCacheTest implements WithAssertions {

    @Autowired
    private MessageFactory beanWithCache;

    @Test
    @Disabled("should fail, for demo only")
    void beanWithCacheUsesCache() {
        BeanWithCache.Descriptor hello = new BeanWithCache.Descriptor("Hello", "something");
        BeanWithCache.Descriptor hello2 = new BeanWithCache.Descriptor("Hello", "other");
        BeanWithCache.Descriptor goodbye = new BeanWithCache.Descriptor("Goodbye", "something");

        beanWithCache.getMessage(hello);
        beanWithCache.getMessage(hello2);
        beanWithCache.getMessage(goodbye);

        BeanWithCache mock = AopTestUtils.getUltimateTargetObject(beanWithCache);

        verify(mock).getMessage(hello);
        verify(mock).getMessage(goodbye);
        verifyNoMoreInteractions(beanWithCache);
    }

    @Configuration
    @ComponentScan(basePackages = "com.mikaelfrancoeur.testingspringboot.cache")
    static class Config {
        @Bean
        BeanWithCache beanWithCache() {
            return mock();
        }
    }
}
