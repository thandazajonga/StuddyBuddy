package com.thandazajonga.studdybuddy.service;

import com.thandazajonga.studdybuddy.config.SecurityConfiguration;
import com.thandazajonga.studdybuddy.dto.LoginRequest;
import com.thandazajonga.studdybuddy.dto.RegisterRequest;
import com.thandazajonga.studdybuddy.entity.User;
import com.thandazajonga.studdybuddy.repository.UserRepository;
import com.thandazajonga.studdybuddy.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequest requestUser) {
        User user= new User();
        user.setName(requestUser.getName());
        user.setEmail(requestUser.getEmail());
        user.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        return userRepository.save(user);
    }

    public String login(LoginRequest requestUser){
        User user = userRepository.findByEmail(requestUser.getEmail()).orElse(null);
        if(user == null){
            return "User not found";
        }

        boolean passwordMatch = passwordEncoder.matches(requestUser.getPassword(), user.getPassword());
        if(!passwordMatch){
            return "Invalid password";
        }
        return jwtService.generateToken(user.getEmail());
    }
}
