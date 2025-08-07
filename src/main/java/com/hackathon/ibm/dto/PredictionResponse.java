package com.hackathon.ibm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredictionResponse {
    private String prediction;

    @JsonProperty("risk_score")
    private Double riskScore;

    @JsonProperty("top_shap_features")
    private Map<String, Double> topShapFeatures;

    @JsonProperty("firewall_recommendations")
    private List<String> firewallRecommendations;
}
