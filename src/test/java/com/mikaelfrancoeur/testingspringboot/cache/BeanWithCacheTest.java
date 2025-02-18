package com.mikaelfrancoeur.testingspringboot.cache;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.util.AopTestUtils;

@SpringBootTest(classes = {
        CacheConfig.class,
        BeanWithCacheTest.TestConfig.class
})
@ImportAutoConfiguration(CacheAutoConfiguration.class)
public class BeanWithCacheTest implements WithAssertions {

    @Autowired
    private BeanWithCache beanWithCache;

    @Test
    void beanWithCacheUsesCache() {
        BeanWithCache.Descriptor hello = new BeanWithCache.Descriptor("Hello", "something");
        BeanWithCache.Descriptor hello2 = new BeanWithCache.Descriptor("Hello", "other");
        BeanWithCache.Descriptor goodbye = new BeanWithCache.Descriptor("Goodbye", "something");

        beanWithCache.getMessage(hello);
        beanWithCache.getMessage(hello2);
        beanWithCache.getMessage(goodbye);
        beanWithCache.getMessage(goodbye);

        BeanWithCache mock = AopTestUtils.getUltimateTargetObject(beanWithCache);

        verify(mock).getMessage(hello);
        verify(mock).getMessage(goodbye);
        verifyNoMoreInteractions(beanWithCache);
    }

    @Configuration
    static class TestConfig {
        @Bean
        BeanWithCache myMockBean() {
            // if you're not on Mockito 5, make sure to use the "inline" mock maker
            // see https://stackoverflow.com/a/40018295/7096763
            return Mockito.mock();
        }
    }
}
