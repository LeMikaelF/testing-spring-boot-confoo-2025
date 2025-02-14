package com.mikaelfrancoeur.testingspringboot.validation;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.validation.ConstraintViolationException;

@Import(DeviceService.class)
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(ValidationAutoConfiguration.class)
public class DeviceServiceTest implements WithAssertions {

    @Autowired
    private DeviceService deviceService;

    @Test
    void validationFails() {
        assertThatThrownBy(() -> deviceService.save(new Device("serialNumber", Device.Model.ANDROID, "")))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessage("save.device.location: must not be empty");
    }

}
