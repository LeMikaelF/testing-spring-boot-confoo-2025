package com.mikaelfrancoeur.testingspringboot.contextcaching;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mikaelfrancoeur.testingspringboot.slow.SlowBean;

//FIXME don't specify at class level
@EnableScheduling
@SpringBootTest(classes = {
        Bean1.class,
        Bean2.class,
        SlowBean.class,
})
@Retention(RetentionPolicy.RUNTIME)
public @interface DemoIT {
}
