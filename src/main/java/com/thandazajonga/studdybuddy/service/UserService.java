package com.thandazajonga.studdybuddy.service;

import com.thandazajonga.studdybuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
}
