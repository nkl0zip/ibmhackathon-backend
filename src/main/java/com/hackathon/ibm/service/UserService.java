package com.hackathon.ibm.service;

import com.hackathon.ibm.model.AccessLevel;
import com.hackathon.ibm.model.Role;
import com.hackathon.ibm.model.User;
import com.hackathon.ibm.dto.UserDto;
import com.hackathon.ibm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Helper to generate new employee ID
    public String generateNextEmployeeId() {
        Optional<User> lastUser = userRepository.findTopByOrderByIdDesc();
        long next = lastUser.map(user -> user.getId() + 1).orElse(1L);
        return String.format("EMP_%04d", next);
    }

    public User createEmployee(String name, String email, String password, String department, AccessLevel access, Integer performance, Integer salaryBand, Integer tenureMonths) {
        String employeeId = generateNextEmployeeId();
        User user = User.builder()
                .employeeId(employeeId)
                .department(department)
                .accessLevel(access == null ? AccessLevel.LOW : access)
                .performance(performance == null ? 0 : performance)
                .salaryBand(salaryBand == null ? 1 : salaryBand)
                .tenureMonths(tenureMonths == null ? 0 : tenureMonths)
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.EMPLOYEE)
                .build();
        return userRepository.save(user);
    }

    public User createAdmin(String name, String email, String password) {
        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.ADMIN)
                .build();
        return userRepository.save(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Map to DTOs to avoid sending password hashes
        return users.stream().map(user ->
                UserDto.builder()
                        .id(user.getId())
                        .employeeId(user.getEmployeeId())
                        .department(user.getDepartment())
                        .accessLevel(user.getAccessLevel())
                        .performance(user.getPerformance())
                        .salaryBand(user.getSalaryBand())
                        .tenureMonths(user.getTenureMonths())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build()
        ).collect(Collectors.toList());
    }
}
