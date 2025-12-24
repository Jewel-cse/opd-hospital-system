package com.ztrios.opd_doctor_service.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Unit tests for DemoController in Doctor Service.
 * Uses @SpringBootTest with @AutoConfigureMockMvc for full application context
 * testing.
 */
@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /test - Should return Hello World message")
    void hello_ShouldReturnHelloWorld() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World")));
    }

    @Test
    @DisplayName("GET /test - Should return OK status")
    void hello_ShouldReturnOkStatus() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /test - Response should not be empty")
    void hello_ResponseShouldNotBeEmpty() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyString())));
    }

    @Test
    @DisplayName("GET /test - Response content type should be text")
    void hello_ShouldReturnTextContent() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"));
    }
}
