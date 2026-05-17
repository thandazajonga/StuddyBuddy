package com.thandazajonga.studdybuddy.controller;

import com.thandazajonga.studdybuddy.dto.LoginRequest;
import com.thandazajonga.studdybuddy.service.AuthenticationService;
import com.thandazajonga.studdybuddy.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest requestUser){
        return ResponseEntity.ok(authenticationService.register(requestUser));
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest requestUser){
        return ResponseEntity.ok(authenticationService.login(requestUser));
    }
}
