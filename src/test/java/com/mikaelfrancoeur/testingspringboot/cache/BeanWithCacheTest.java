package com.mikaelfrancoeur.testingspringboot.cache;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = BeanWithCacheTest.Config.class)
@ImportAutoConfiguration(CacheAutoConfiguration.class)
public class BeanWithCacheTest implements WithAssertions {

    @Autowired
    private BeanWithCache beanWithCache;

    @Test
    @Disabled("should fail, for demo only")
    void beanWithCacheUsesCache() {
        String message1 = beanWithCache.getMessage(new BeanWithCache.Descriptor("Hello"));
        String message2 = beanWithCache.getMessage(new BeanWithCache.Descriptor("Hello"));

        assertThat(message1).isEqualTo(message2);

        String message3 = beanWithCache.getMessage(new BeanWithCache.Descriptor("Goodbye"));
        assertThat(message1).isNotEqualTo(message3);
    }

    @ComponentScan(basePackages = "com.mikaelfrancoeur.testingspringboot.cache")
    static class Config {
    }
}
