package com.ztrios.opd_auth_service.controller;

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
 * Unit tests for HealthController in Auth Service.
 * Uses @SpringBootTest with @AutoConfigureMockMvc for full application context
 * testing.
 */
@SpringBootTest
@AutoConfigureMockMvc
class HealthControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        @DisplayName("GET /health - Should return UP status")
        void health_ShouldReturnUpStatus() throws Exception {
                mockMvc.perform(get("/health"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status", is("UP")))
                                .andExpect(jsonPath("$.service").exists())
                                .andExpect(jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("GET /actuator/health - Should return UP status")
        void actuatorHealth_ShouldReturnUpStatus() throws Exception {
                mockMvc.perform(get("/actuator/health"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status", is("UP")));
        }

        @Test
        @DisplayName("GET /info - Should return service information")
        void info_ShouldReturnServiceInfo() throws Exception {
                mockMvc.perform(get("/info"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.service").exists())
                                .andExpect(jsonPath("$.port").exists())
                                .andExpect(jsonPath("$.description",
                                                is("Authentication and Authorization Service for OPD Hospital System")))
                                .andExpect(jsonPath("$.version", is("1.0.0")));
        }

        @Test
        @DisplayName("GET /actuator/info - Should return service information")
        void actuatorInfo_ShouldReturnServiceInfo() throws Exception {
                mockMvc.perform(get("/actuator/info"))
                                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Health endpoints should be accessible without authentication")
        void healthEndpoints_ShouldBePubliclyAccessible() throws Exception {
                // These endpoints should not require authentication
                mockMvc.perform(get("/health"))
                                .andExpect(status().isOk());

                mockMvc.perform(get("/info"))
                                .andExpect(status().isOk());
        }
}
