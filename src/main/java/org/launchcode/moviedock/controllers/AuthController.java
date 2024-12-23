package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.moviedock.models.dto.SignupFormDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSuccess() {return "user/profile"; }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/settings")
    public String displaySettings(Model model, HttpServletRequest request) {

        model.addAttribute(new SignupFormDTO());
        model.addAttribute("title", "Account Settings");

        return "user/settings";
    }
}
