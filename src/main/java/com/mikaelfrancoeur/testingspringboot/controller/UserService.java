package com.mikaelfrancoeur.testingspringboot.controller;

import jakarta.validation.Valid;

interface UserService {

    User upsert(@Valid User id, String name);
}
