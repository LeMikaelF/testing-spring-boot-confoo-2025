package com.mikaelfrancoeur.testingspringboot.security;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authorization.AuthorizationDeniedException;

@SpringBootTest(classes = SecuredBeanTest.Config.class)
class SecuredBeanTest implements WithAssertions {

    @Autowired
    private SecuredBean securedBean;

    @Test
    void whenArgIsFalse_thenAccessDenied() {
        assertThatThrownBy(() -> securedBean.onlyAllowedIfArgIsTrue(false))
                .isInstanceOf(AuthorizationDeniedException.class);
    }

    // In some specific cases, @ComponentScan is only picked up if it is on a separate class.
    @ComponentScan(basePackages = "com.mikaelfrancoeur.testingspringboot.security")
    static class Config {
    }
}
