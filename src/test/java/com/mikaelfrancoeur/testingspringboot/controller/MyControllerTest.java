package com.mikaelfrancoeur.testingspringboot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MyController.class)
class MyControllerTest {

    private static final String PRINCIPAL_NAME = "principal name";
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Mock
    private Principal principal;

    @Test
    void whenRequestPayloadIsValid_thenCallsServiceWithPrincipalName() throws Exception {
        when(principal.getName()).thenReturn(PRINCIPAL_NAME);
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(principal)
                        .content("""
                                {
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "sin": "123 456 789",
                                  "reason": "something something something something something"
                                }
                                """))
                .andExpect(status().isOk());

        verify(userService).upsert(any(), eq(PRINCIPAL_NAME));
    }


    @Test
    void whenRequestPayloadIsInvalid_thenBadRequest() throws Exception {
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "sin": "not valid",
                                  "reason": "something something something something something"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("social insurance number not valid"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

}
