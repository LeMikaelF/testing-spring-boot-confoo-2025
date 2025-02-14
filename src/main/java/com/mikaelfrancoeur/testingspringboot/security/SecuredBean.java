package com.mikaelfrancoeur.testingspringboot.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
class SecuredBean {

    @PreAuthorize("#arg == true")
    void onlyAllowedIfArgIsTrue(boolean arg) {
    // no-op
    }
}
