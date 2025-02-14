package com.mikaelfrancoeur.testingspringboot.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
class MyAspect {

    @AfterThrowing(
            pointcut = "(@target(loggedException) || @annotation(loggedException)) && execution (public * *.save*(..))",
            throwing = "exception")
    void afterThrowing(LoggedException loggedException, Exception exception) {
        log.atLevel(loggedException.level()).log("Exception thrown: ", exception);
    }
}
