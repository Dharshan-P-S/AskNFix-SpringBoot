package com.SpringBoot.AskNFix.controller;

import com.SpringBoot.AskNFix.dto.LoginRequest;
import com.SpringBoot.AskNFix.dto.LoginResponse;
import com.SpringBoot.AskNFix.dto.StudentRegisterRequest;
import com.SpringBoot.AskNFix.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/student/login")
    public LoginResponse studentLogin(
            @RequestBody LoginRequest request) {

        return authService.studentLogin(request);
    }

    @PostMapping("/staff/login")
    public LoginResponse staffLogin(
            @RequestBody LoginRequest request) {

        return authService.staffLogin(request);
    }

    @PostMapping("/student/register")
    public String studentRegister(
            @RequestBody StudentRegisterRequest request) {

        return authService.studentRegister(request);
    }
}