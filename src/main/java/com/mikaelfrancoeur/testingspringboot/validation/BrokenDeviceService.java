package com.mikaelfrancoeur.testingspringboot.validation;

import org.springframework.validation.annotation.Validated;

@Validated
@SuppressWarnings("unused")
class BrokenDeviceService {

    public void save(@Validated(Device.PhysicalValidation.class) Device device) {
        // save the device
    }
}
