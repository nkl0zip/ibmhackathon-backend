package com.hackathon.ibm.controller;

import com.hackathon.ibm.dto.LoginRequest;
import com.hackathon.ibm.dto.LoginResponse;
import com.hackathon.ibm.dto.SignupRequest;
import com.hackathon.ibm.model.Role;
import com.hackathon.ibm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signupEmployee(@RequestBody SignupRequest request) {
        authService.signupEmployee(request);
        return ResponseEntity.ok("Signup successful");
    }

    @PostMapping("/login/employee")
    public ResponseEntity<LoginResponse> loginEmployee(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request, Role.EMPLOYEE));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<LoginResponse> loginAdmin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request, Role.ADMIN));
    }
}
