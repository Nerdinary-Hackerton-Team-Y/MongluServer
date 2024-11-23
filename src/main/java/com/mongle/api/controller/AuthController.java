package com.mongle.api.controller;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongle.api.dto.auth.RegisterDto;
import com.mongle.api.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public void login(
        @RequestParam(name = "username", required = true) String username,
        @RequestParam(name = "password", required = true) String password,
        HttpServletResponse response
    ) {
        String token = authService.login(username, password);
        response.addHeader("Authorization", "Bearer " + token);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterDto dto) {
        authService.register(dto);
    }
}
