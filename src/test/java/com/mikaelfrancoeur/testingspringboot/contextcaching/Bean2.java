package com.mikaelfrancoeur.testingspringboot.contextcaching;

import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bean2 {
    @Scheduled(fixedDelayString = "1000")
    void log() {
        log.info("Context 2 is running");
    }
}
