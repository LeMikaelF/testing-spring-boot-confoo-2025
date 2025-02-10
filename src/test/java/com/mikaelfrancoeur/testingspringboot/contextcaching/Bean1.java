package com.mikaelfrancoeur.testingspringboot.contextcaching;

import static org.zalando.fauxpas.FauxPas.throwingRunnable;

import org.springframework.scheduling.annotation.Scheduled;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bean1 {

    @Scheduled(fixedDelayString = "1000")
    void log() {
        log.info("Context 1 is running");
    }
}
