package com.mikaelfrancoeur.testingspringboot.validation;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@ExtendWith(SpringExtension.class)
@Import(DeviceTest.ValidationHelper.class)
@ImportAutoConfiguration(ValidationAutoConfiguration.class)
class DeviceTest implements WithAssertions {

    @Autowired
    private ValidationHelper validationHelper;

    @Test
    void invalidDefaultGroup() {
        assertThatThrownBy(() -> validationHelper.validate(new Device("123-4567-D", null, "")))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("validate.device.serialNumber: must match \"\\d{3}-\\d{4}-[A-C]\"")
                .hasMessageContaining("validate.device.model: must not be null");
    }

    @Test
    void validDefaultGroup() {
        validationHelper.validate(new Device("123-4567-A", Device.Model.ANDROID, "location"));
    }

    @Test
    void invalidPhysicalGroup() {
        assertThatThrownBy(() -> validationHelper.validatePhysicalGroup(new Device("123-4567-A", Device.Model.ANDROID, null)))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("validatePhysicalGroup.device.location: must not be empty");
    }

    @Validated
    static class ValidationHelper {
        void validate(@Valid Device device) {
            // no-op
        }

        @Validated(Device.PhysicalValidation.class)
        void validatePhysicalGroup(@Valid Device device) {
            // no-op
        }
    }
}
