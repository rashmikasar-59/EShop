package com.shoppingCart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingCart.request.CreateUserRequest;
import com.shoppingCart.request.LoginRequest;
import com.shoppingCart.response.ApiResponse;
import com.shoppingCart.response.AuthResponse;
import com.shoppingCart.service.security.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody CreateUserRequest request) {
        String token = authService.register(request);
        return ResponseEntity.ok(new ApiResponse(
                "Registration successful",
                new AuthResponse(token, "User registered successfully")));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new ApiResponse(
               "Login successful",
                new AuthResponse(token, "User logged in successfully")));
    }
}
