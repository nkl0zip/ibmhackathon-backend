package com.hackathon.ibm.service;

import com.hackathon.ibm.dto.ModelDataDto;
import com.hackathon.ibm.model.DeviceType;
import com.hackathon.ibm.model.ModelData;
import com.hackathon.ibm.model.User;
import com.hackathon.ibm.repository.ModelDataRepository;
import com.hackathon.ibm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModelDataService {
    private final ModelDataRepository modelDataRepository;
    private final UserRepository userRepository;

    // Add a new model data row (calculate session_duration here)
    @Transactional
    public ModelDataDto addModelData(Long userId, LocalDateTime sessionStart, LocalDateTime sessionEnd,
                                     String action, DeviceType deviceType, String location, String sourceIp) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Day of week logic
        LocalDate sessionDate = sessionStart.toLocalDate();
        int currentDayOfWeek = sessionDate.getDayOfWeek().getValue(); // 1=Mon,...7=Sun

        // Calculate session duration in minutes
        int sessionDuration = (int) Duration.between(sessionStart, sessionEnd).toMinutes();

        // Check weekend
        boolean isWeekend = (currentDayOfWeek == 6 || currentDayOfWeek == 7);

        // Check off hours (between 6PM and 9AM)
        int startHour = sessionStart.getHour();
        int endHour = sessionEnd.getHour();
        boolean isOffHours = (startHour >= 18 || startHour < 9) || (endHour >= 18 || endHour < 9);

        // Fetch last entry for week to get the day_of_week, or reset to 1 if new week (Monday)
        int dayOfWeekCount = 1;
        List<ModelData> userWeekData = modelDataRepository.findByUser(user);
        LocalDate monday = sessionDate.with(DayOfWeek.MONDAY);
        long countThisWeek = userWeekData.stream()
                .filter(md -> !md.getSessionStart().toLocalDate().isBefore(monday))
                .count();
        dayOfWeekCount = (int) countThisWeek + 1;

        ModelData modelData = ModelData.builder()
                .user(user)
                .dayOfWeek(dayOfWeekCount)
                .isWeekend(isWeekend)
                .isOffHours(isOffHours)
                .action(action)
                .deviceType(deviceType)
                .location(location)
                .sourceIp(sourceIp)
                .sessionStart(sessionStart)
                .sessionEnd(sessionEnd)
                .sessionDuration(sessionDuration)
                .build();
        modelData = modelDataRepository.save(modelData);

        return ModelDataDto.builder()
                .id(modelData.getId())
                .userId(user.getId())
                .dayOfWeek(modelData.getDayOfWeek())
                .isWeekend(modelData.getIsWeekend())
                .isOffHours(modelData.getIsOffHours())
                .action(modelData.getAction())
                .deviceType(modelData.getDeviceType())
                .location(modelData.getLocation())
                .sourceIp(modelData.getSourceIp())
                .sessionStart(modelData.getSessionStart())
                .sessionEnd(modelData.getSessionEnd())
                .sessionDuration(modelData.getSessionDuration())
                .build();
    }

    // Fetch all records for a user
    public List<ModelDataDto> getModelDataForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return modelDataRepository.findByUser(user)
                .stream()
                .map(md -> ModelDataDto.builder()
                        .id(md.getId())
                        .userId(md.getUser().getId())
                        .dayOfWeek(md.getDayOfWeek())
                        .isWeekend(md.getIsWeekend())
                        .isOffHours(md.getIsOffHours())
                        .action(md.getAction())
                        .deviceType(md.getDeviceType())
                        .location(md.getLocation())
                        .sourceIp(md.getSourceIp())
                        .sessionStart(md.getSessionStart())
                        .sessionEnd(md.getSessionEnd())
                        .sessionDuration(md.getSessionDuration())
                        .build())
                .collect(Collectors.toList());
    }

    // Reset all users' day_of_week to 0 on Monday
    @Transactional
    public void resetDayOfWeekOnMonday() {
        LocalDate today = LocalDate.now();
        if (today.getDayOfWeek() == DayOfWeek.MONDAY) {
            List<ModelData> all = modelDataRepository.findAll();
            for (ModelData md : all) {
                md.setDayOfWeek(0);
                modelDataRepository.save(md);
            }
        }
    }
}
