package com.example.spring_boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Show the custom login page (GET /login)
    @GetMapping("/login")
    public String showLoginPage() {
        // returns Thymeleaf template "login.html"
        return "login";
    }
}
