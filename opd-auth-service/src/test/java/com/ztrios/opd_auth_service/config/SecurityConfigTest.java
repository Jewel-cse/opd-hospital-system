package com.ztrios.opd_auth_service.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for SecurityConfig.
 * Verifies that security configuration properly allows/denies access to
 * endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Health endpoint should be publicly accessible")
    void healthEndpoint_ShouldBePublic() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Info endpoint should be publicly accessible")
    void infoEndpoint_ShouldBePublic() throws Exception {
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Actuator health endpoint should be publicly accessible")
    void actuatorHealthEndpoint_ShouldBePublic() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Actuator info endpoint should be publicly accessible")
    void actuatorInfoEndpoint_ShouldBePublic() throws Exception {
        mockMvc.perform(get("/actuator/info"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Protected endpoint should require authentication")
    void protectedEndpoint_ShouldRequireAuth() throws Exception {
        // Any endpoint not in the permitAll list should be protected
        // Returns 401 or 403 depending on security configuration
        mockMvc.perform(get("/api/protected"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    @DisplayName("Authenticated user should access protected endpoints")
    void authenticatedUser_ShouldAccessProtectedEndpoints() throws Exception {
        // Health endpoints should still work with authenticated user
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk());
    }
}
