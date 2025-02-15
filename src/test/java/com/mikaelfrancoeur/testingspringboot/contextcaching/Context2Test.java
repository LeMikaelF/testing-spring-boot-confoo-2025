package com.mikaelfrancoeur.testingspringboot.contextcaching;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mikaelfrancoeur.testingspringboot.slow.SlowBean;

@SpringBootTest(classes = {
        Bean2.class,
        SlowBean.class,
})
public class Context2Test {

    @Test
    void test() {
        // no-op
    }
}
