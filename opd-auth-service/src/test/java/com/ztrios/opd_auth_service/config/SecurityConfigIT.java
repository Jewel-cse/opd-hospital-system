package com.ztrios.opd_auth_service.config;

import com.ztrios.opd_auth_service.testconfig.AbstractPostgresIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for Spring Security configuration.
 * Tests that security configuration works correctly with real application
 * context.
 */
class SecurityConfigIT extends AbstractPostgresIT {

    @Test
    @DisplayName("IT: Public endpoints should be accessible without authentication")
    void publicEndpoints_ShouldBeAccessible() throws Exception {
        // Health endpoint
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk());

        // Info endpoint
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("IT: Protected endpoints should require authentication")
    void protectedEndpoints_ShouldRequireAuth() throws Exception {
        mockMvc.perform(get("/api/protected"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    @DisplayName("IT: Authenticated user should access public endpoints")
    void authenticatedUser_ShouldAccessPublicEndpoints() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/info"))
                .andExpect(status().isOk());
    }
}
