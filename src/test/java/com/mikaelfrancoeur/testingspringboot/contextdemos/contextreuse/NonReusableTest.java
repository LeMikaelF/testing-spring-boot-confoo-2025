package com.mikaelfrancoeur.testingspringboot.contextdemos.contextreuse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@EmbeddedKafka
@SpringBootTest
class NonReusableTest {

    @MockitoBean
    private MyBean myBean;

    @Test
    void test() {
        System.out.println("This is the non-reusable test");
    }

    static class MyBean {

    }
}
