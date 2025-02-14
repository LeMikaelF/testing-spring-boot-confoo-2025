package com.mikaelfrancoeur.testingspringboot.security;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//FIXME why do I need a new class
@ExtendWith(SpringExtension.class)
@Import(SecuredBeanTest.Config.class)
@ComponentScan(basePackages = "com.mikaelfrancoeur.testingspringboot.security")
class SecuredBeanTest implements WithAssertions {

    @Autowired
    private SecuredBean securedBean;

    @Test
    void whenArgIsFalse_thenAccessDenied() {
        assertThatThrownBy(() -> securedBean.onlyAllowedIfArgIsTrue(false))
                .isInstanceOf(AuthorizationDeniedException.class);
    }

    static class Config {
    }
}
