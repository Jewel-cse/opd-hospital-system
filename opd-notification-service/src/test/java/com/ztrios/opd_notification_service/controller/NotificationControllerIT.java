package com.ztrios.opd_notification_service.controller;

import com.ztrios.opd_notification_service.testconfig.AbstractIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for Notification Service controller endpoints.
 * No database container required for this service.
 */
class NotificationControllerIT extends AbstractIT {

    @Test
    @DisplayName("IT: GET /health - Should return UP status")
    void health_ShouldReturnUpStatus() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("UP")))
                .andExpect(jsonPath("$.service", is("opd-notification-service")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("IT: GET /info - Should return service information")
    void info_ShouldReturnServiceInfo() throws Exception {
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("opd-notification-service")))
                .andExpect(jsonPath("$.port").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.version", is("1.0.0")));
    }

    @Test
    @DisplayName("IT: GET /actuator/info - Should be accessible")
    void actuatorInfo_ShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/actuator/info"))
                .andExpect(status().isOk());
    }
}
