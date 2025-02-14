package com.mikaelfrancoeur.testingspringboot.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import lombok.SneakyThrows;

@WebMvcTest(controllers = MyController.class)
class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @SneakyThrows
    void whenRequestPayloadIsInvalid_thenBadRequest() {
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"sin\":\"not valid\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("social insurance number not valid"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

}
