package com.mikaelfrancoeur.testingspringboot.kafka;

import java.util.Date;
import java.util.UUID;

import org.apache.avro.reflect.AvroIgnore;
import org.apache.avro.reflect.AvroName;
import org.apache.avro.reflect.Stringable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
class MyPayload {
    @AvroName("theMessage") private String message;
    @Stringable private Date date;
    @AvroIgnore private UUID uuid;
    @Stringable private Email email;

    @Getter
    static class Email {
        private final String user;
        private final String domain;

        Email(String address) {
            String[] parts = address.split("@");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid email address: " + address);
            }
            user = parts[0];
            domain = parts[1];
        }

        @Override
        @JsonSerialize
        public String toString() {
            return user + "@" + domain;
        }
    }
}
