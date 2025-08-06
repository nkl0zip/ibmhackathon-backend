package com.hackathon.ibm.service;
import com.hackathon.ibm.dto.LoginRequest;
import com.hackathon.ibm.dto.LoginResponse;
import com.hackathon.ibm.dto.SignupRequest;
import com.hackathon.ibm.service.LoginDataService;
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
    private final LoginDataService loginDataService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request, Role expectedRole) {
        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // On failed attempt, increment counter
            loginDataService.incrementFailedLoginAttempts(user);
            throw new RuntimeException("Invalid email or password");
        }

        if (user.getRole() != expectedRole) {
            throw new RuntimeException("Unauthorized login for this role");
        }

        // On successful login, you would track login session time
        // For now, let's simulate adding 1 hour on each login
        loginDataService.addLoginHours(user, 1);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new LoginResponse(token, user.getName(), user.getEmail(), user.getRole().name());
    }

    public void signupEmployee(SignupRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        userService.createEmployee(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getDepartment(),
                request.getAccessLevel(),
                request.getPerformance(),
                request.getSalaryBand(),
                request.getTenureMonths()
        );
    }
}
