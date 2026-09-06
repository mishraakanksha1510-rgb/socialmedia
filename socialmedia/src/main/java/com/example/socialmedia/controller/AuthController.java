package com.example.socialmedia.controller;

import com.example.socialmedia.dto.RegisterRequest;
import com.example.socialmedia.service.AuthService;
import org.springframework.web.bind.annotation.*;
import com.example.socialmedia.dto.Loginreq;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String test() {
        return "Authenticated user";
    }


    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody Loginreq request) {
        return authService.login(request);
    }
}