package com.mikaelfrancoeur.testingspringboot.controller;

import org.springframework.stereotype.Component;

@Component
class DefaultUserService implements UserService {
    @Override
    public User upsert(User id, String name) {
        return null;
    }
}
