package com.mikaelfrancoeur.testingspringboot.contextcaching;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mikaelfrancoeur.testingspringboot.slow.SlowBean;

@SpringBootTest(classes = {
        Bean1.class,
        SlowBean.class
})
class Context1Test {

    @Test
    void test() {
        // no-op
    }
}
