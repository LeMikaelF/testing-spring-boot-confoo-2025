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

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class MyController {
    private final UserService userService;

    @PutMapping
    User upsertUser(@RequestBody @Valid UpsertRequest request) {
        return userService.upsert(request.user);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    String handleConstraintViolationException(MethodArgumentNotValidException exception) {
        return StringUtils.arrayToCommaDelimitedString(exception.getAllErrors().stream().map(ObjectError::getDefaultMessage).toArray());
    }

    @Data
    private static class UpsertRequest {
        @Valid
        @JsonUnwrapped
        private User user;
        @NotEmpty
        @Size(min = 15)
        private String reason;
    }
}
