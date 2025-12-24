package com.ztrios.opd_doctor_service.controller;

import com.ztrios.opd_doctor_service.testconfig.AbstractPostgresIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for Doctor Service controller endpoints.
 * Uses PostgreSQL Testcontainer for realistic database testing.
 */
class DoctorControllerIT extends AbstractPostgresIT {

    @Test
    @DisplayName("IT: GET /health - Should return UP status with database connected")
    void health_ShouldReturnUpStatus_WithDatabaseConnected() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("UP")))
                .andExpect(jsonPath("$.service", is("opd-doctor-service")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("IT: GET /info - Should return service information")
    void info_ShouldReturnServiceInfo() throws Exception {
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("opd-doctor-service")))
                .andExpect(jsonPath("$.port").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.version", is("1.0.0")));
    }

    @Test
    @DisplayName("IT: GET /test - Should return Hello World message")
    void demoEndpoint_ShouldReturnHelloWorld() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World")));
    }

    @Test
    @DisplayName("IT: GET /actuator/info - Should be accessible")
    void actuatorInfo_ShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/actuator/info"))
                .andExpect(status().isOk());
    }
}
