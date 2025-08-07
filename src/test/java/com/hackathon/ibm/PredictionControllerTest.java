package com.hackathon.ibm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.ibm.controller.PredictionController;
import com.hackathon.ibm.dto.PredictionResponse;
import com.hackathon.ibm.service.PredictionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PredictionController.class)
@AutoConfigureMockMvc(addFilters = false)
class PredictionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PredictionService predictionService;

    @Test
    void shouldReturnPredictionResponseWithAllFields() throws Exception {
        Map<String, Object> sampleFeatures = Map.of("bytes_in", 100, "bytes_out", 50);

        PredictionResponse mockResponse = PredictionResponse.builder()
                .prediction("anomaly")
                .riskScore(0.95)
                .topShapFeatures(Map.of("bytes_in", 0.6, "bytes_out", -0.2))
                .firewallRecommendations(List.of("Block IP 10.0.0.1", "Throttle connection"))
                .build();

        when(predictionService.predict(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/api/predict")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleFeatures)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prediction").value("anomaly"))
                .andExpect(jsonPath("$.risk_score").value(0.95))
                .andExpect(jsonPath("$.top_shap_features.bytes_in").value(0.6))
                .andExpect(jsonPath("$.firewall_recommendations[0]").value("Block IP 10.0.0.1"));
    }

    @Test
    void shouldReturnPredictionResponseForDifferentPayload() throws Exception {
        Map<String, Object> sampleFeatures = Map.of("failed_logins", 3, "session_duration", 120);

        PredictionResponse mockResponse = PredictionResponse.builder()
                .prediction("normal")
                .riskScore(0.1)
                .topShapFeatures(Map.of("failed_logins", 0.05))
                .firewallRecommendations(List.of("No action required"))
                .build();

        when(predictionService.predict(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/api/predict")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleFeatures)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prediction").value("normal"))
                .andExpect(jsonPath("$.risk_score").value(0.1))
                .andExpect(jsonPath("$.top_shap_features.failed_logins").value(0.05))
                .andExpect(jsonPath("$.firewall_recommendations[0]").value("No action required"));
    }
}
