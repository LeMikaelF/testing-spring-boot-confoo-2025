package com.mikaelfrancoeur.testingspringboot.contextcaching;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootTest(classes = Bean2.class)
class Context2Test {

    @Test
    void test() throws InterruptedException {
        Thread.sleep(10_000);
    }
}
