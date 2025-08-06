package com.hackathon.ibm.service;
import com.hackathon.ibm.dto.LoginRequest;
import com.hackathon.ibm.dto.LoginResponse;
import com.hackathon.ibm.dto.SignupRequest;
import com.hackathon.ibm.model.User;
import com.hackathon.ibm.model.Role;
import com.hackathon.ibm.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request, Role expectedRole) {
        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (user.getRole() != expectedRole) {
            throw new RuntimeException("Unauthorized login for this role");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new LoginResponse(token, user.getName(), user.getEmail(), user.getRole().name());
    }

    public void signupEmployee(SignupRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        userService.createEmployee(request.getName(), request.getEmail(), request.getPassword());
    }
}
