package com.example.spring_boot.controller;

import com.example.spring_boot.entity.User;
import com.example.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    // Display the registration form (GET /register)
    @GetMapping("/register")
    public String showRegisterForm() {
        // returns the Thymeleaf template "register.html"
        return "register";
    }

    // Process the registration form (POST /register)
    @PostMapping("/register")
    public String processRegister(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            @RequestParam(value = "role", defaultValue = "ROLE_PATIENT") String role,
            Model model
    ) {
        // Optionally validate password match
        if (confirmPassword != null && !password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "register";
        }

        // Create new user (hashed password handled by UserService)
        User newUser = new User(username, password, role);
        // If the user can be a patient/doctor, link them to that entity if needed

        // attempt to create user (watch out for duplicates, etc.)
        try {
            userService.createUser(newUser);
        } catch (Exception e) {
            // e.g., if user already exists
            model.addAttribute("error", "Could not register user: " + e.getMessage());
            return "register";
        }

        // success -> redirect to login page
        return "redirect:/login";
    }
}
