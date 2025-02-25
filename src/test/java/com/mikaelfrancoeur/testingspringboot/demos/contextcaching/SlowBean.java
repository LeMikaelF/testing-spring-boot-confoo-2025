package com.mikaelfrancoeur.testingspringboot.demos.contextcaching;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class SlowBean {

    @PostConstruct
    void postConstruct() throws InterruptedException {
        log.info("initializing slow bean");
        // slow initialization (maybe DB, tracing, messaging, etc.)
        Thread.sleep(5_000);
    }
}
