package com.mikaelfrancoeur.testingspringboot.slow;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SlowBean {

    @PostConstruct
    void postConstruct() throws InterruptedException {
        // slow initialization (maybe DB, tracing, messaging, etc.)
        Thread.sleep(5_000);
    }
}
