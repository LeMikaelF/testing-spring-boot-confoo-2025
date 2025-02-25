package com.mikaelfrancoeur.testingspringboot.demos.contextcaching;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
        Bean1.class,
        ContextCachingConfig.class,
        SlowBean.class
})
class Context1Test {

    @Test
    void test() {
        // no-op
    }
}
