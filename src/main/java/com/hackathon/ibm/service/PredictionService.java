package com.hackathon.ibm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.ibm.dto.PredictionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PredictionService {
    private final ObjectMapper objectMapper;

    public PredictionResponse predict(Map<String, Object> features) {
        try {
            String inputJson = objectMapper.writeValueAsString(features);
            ProcessBuilder processBuilder = new ProcessBuilder("python3", "inference.py");
            Process process = processBuilder.start();

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                writer.write(inputJson);
                writer.flush();
            }

            String output;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                output = reader.lines().collect(Collectors.joining());
            }

            String error;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                error = reader.lines().collect(Collectors.joining());
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Python inference failed: " + error);
            }

            return objectMapper.readValue(output, PredictionResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke prediction process", e);
        }
    }
}
