package com.mikaelfrancoeur.testingspringboot.context;

import static org.assertj.core.api.InstanceOfAssertFactories.collection;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.ComponentScan;

class MyConfigTest implements WithAssertions {

    private ApplicationContextRunner runner;

    @ComponentScan("com.mikaelfrancoeur.testingspringboot.context")
    private static class TestConfig {
    }

    @BeforeEach
    void beforeEach() {
        runner = new ApplicationContextRunner()
                .withUserConfiguration(TestConfig.class);
    }

    @Test
    void givenCacheEnabledIsFalse_whenCacheConfig_thenNoCacheManager() {
        runner.withPropertyValues("cache.enabled=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean("concurrentCacheManager");
                });
    }

    @Test
    void givenCacheEnabledIsTrue_whenCacheConfig_thenCacheManager() {
        runner.withPropertyValues("cache.enabled=true", "cache.names=cache1,cache2")
                .run(context -> {
                    assertThat(context).getBean(CacheManager.class)
                            .extracting(CacheManager::getCacheNames)
                            .asInstanceOf(collection(String.class))
                            .containsExactlyInAnyOrder("cache1", "cache2");
                });
    }

    @Test
    void givenCachedEnabledIsMissing_whenCacheConfig_thenNoCacheManager() {
        runner.run(context -> {
            assertThat(context).doesNotHaveBean("concurrentCacheManager");
        });
    }

    @Test
    void givenProfileLocal_whenCacheConfig_thenCacheManager() {
        runner.withPropertyValues("spring.profiles.active=local")
                .run(context -> {
                    assertThat(context).hasBean("concurrentCacheManager");
                });
    }

    @Test
    void givenProfileLocalAndCacheEnabledIsFalse_whenCacheConfig_thenCacheManager() {
        runner.withPropertyValues("spring.profiles.active=local", "cache.enabled=false")
                .run(context -> {
                    assertThat(context).hasBean("concurrentCacheManager");
                });
    }
}
