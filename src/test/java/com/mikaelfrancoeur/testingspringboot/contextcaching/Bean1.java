package com.mikaelfrancoeur.testingspringboot.contextcaching;

import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Bean1 {

    @Scheduled(fixedDelayString = "1000")
    void log() {
        log.info("Context 1 is running");
    }
}
