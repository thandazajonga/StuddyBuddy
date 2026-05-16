package com.thandazajonga.studdybuddy.service;

import com.thandazajonga.studdybuddy.config.SecurityConfiguration;
import com.thandazajonga.studdybuddy.dto.RegisterRequest;
import com.thandazajonga.studdybuddy.entity.User;
import com.thandazajonga.studdybuddy.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User register(RegisterRequest requestUser) {
        User user= new User();
        user.setName(requestUser.getName());
        user.setEmail(requestUser.getEmail());
        user.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        return userRepository.save(user);
    }
}
