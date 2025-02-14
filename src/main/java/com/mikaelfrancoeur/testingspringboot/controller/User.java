package com.mikaelfrancoeur.testingspringboot.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

record User(
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        // just an example, not actually a good regex for emails
        @JsonProperty("sin")
        @Pattern(regexp = "\\d{3} \\d{3} \\d{3}", message = "social insurance number not valid") String socialInsuranceNumber
) {
}
