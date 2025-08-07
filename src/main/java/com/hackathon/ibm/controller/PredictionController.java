package com.hackathon.ibm.controller;

import com.hackathon.ibm.dto.PredictionResponse;
import com.hackathon.ibm.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/predict")
@RequiredArgsConstructor
public class PredictionController {
    private final PredictionService predictionService;

    @PostMapping
    public PredictionResponse predict(@RequestBody Map<String, Object> features) {
        return predictionService.predict(features);
    }
}
