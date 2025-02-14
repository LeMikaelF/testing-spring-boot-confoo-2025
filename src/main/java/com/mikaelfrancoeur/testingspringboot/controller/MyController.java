package com.mikaelfrancoeur.testingspringboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
class MyController {
    private final UserService userService;

    @PutMapping
    User upsertUser(@RequestBody @Valid User user) {
        return userService.upsert(user);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    String handleConstraintViolationException(MethodArgumentNotValidException exception) {
        return StringUtils.arrayToCommaDelimitedString(exception.getAllErrors().stream().map(ObjectError::getDefaultMessage).toArray());
    }
}
