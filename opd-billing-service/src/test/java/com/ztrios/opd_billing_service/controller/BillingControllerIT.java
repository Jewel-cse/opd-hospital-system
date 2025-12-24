package com.ztrios.opd_billing_service.controller;

import com.ztrios.opd_billing_service.testconfig.AbstractMongoIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for Billing Service controller endpoints.
 * Uses MongoDB Testcontainer for realistic database testing.
 */
class BillingControllerIT extends AbstractMongoIT {

    @Test
    @DisplayName("IT: GET /health - Should return UP status with MongoDB connected")
    void health_ShouldReturnUpStatus_WithMongoConnected() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("UP")))
                .andExpect(jsonPath("$.service", is("opd-billing-service")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("IT: GET /info - Should return service information")
    void info_ShouldReturnServiceInfo() throws Exception {
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("opd-billing-service")))
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
