package com.mikaelfrancoeur.testingspringboot.validation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record Device(
        @Pattern(regexp = "\\d{3}-\\d{4}-[A-C]") String serialNumber,
        @NotNull Model model,
        @NotEmpty(groups = PhysicalValidation.class) String location
) {

    public enum Model {
        IPHONE, ANDROID, WINDOWS_PHONE
    }

    public interface PhysicalValidation {
    }
}
