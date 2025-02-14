package com.mikaelfrancoeur.testingspringboot.validation;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

@Validated
class DeviceService {

    @Validated(Device.PhysicalValidation.class)
    public void save(@Valid Device device) {
        // save the device
    }
}
