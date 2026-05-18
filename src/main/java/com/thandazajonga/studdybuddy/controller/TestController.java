package com.thandazajonga.studdybuddy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/profile")
    public String testProfile(){
        return "Protected profile endpoint";
    }
}
