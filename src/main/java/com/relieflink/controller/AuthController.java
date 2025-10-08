package com.relieflink.controller;

import com.relieflink.model.User;
import com.relieflink.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        return authService.login(username, password)
                .map(user -> {
                    session.setAttribute("userId", user.getId());
                    session.setAttribute("userRole", user.getRole().name());
                    session.setAttribute("username", user.getUsername());
                    return "{\"success\": true, \"role\": \"" + user.getRole().name() + "\"}";
                })
                .orElse("{\"success\": false, \"message\": \"Invalid credentials\"}");
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@ModelAttribute User user) {
        if (authService.usernameExists(user.getUsername())) {
            return "{\"success\": false, \"message\": \"Username already exists\"}";
        }
        authService.register(user);
        return "{\"success\": true}";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
