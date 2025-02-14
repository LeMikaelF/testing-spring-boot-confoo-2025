package com.mikaelfrancoeur.testingspringboot.slow;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SlowBean {

    @PostConstruct
    void postConstruct() throws InterruptedException {
        log.info("initializing slow bean");
        // slow initialization (maybe DB, tracing, messaging, etc.)
        Thread.sleep(5_000);
    }
}
