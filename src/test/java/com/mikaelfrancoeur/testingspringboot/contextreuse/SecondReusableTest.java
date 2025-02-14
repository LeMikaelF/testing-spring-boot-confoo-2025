package com.mikaelfrancoeur.testingspringboot.contextreuse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka
@SpringBootTest
class SecondReusableTest {

    @Test
    void test() {
        System.out.println("This is the second reusable test");
    }
}
