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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSuccess() {return "user/profile"; }

    @GetMapping("signup")
    public String signinForm() {
        return "signup";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/settings")
    public String displaySettings(Model model) {

        model.addAttribute("title", "Account Settings");

        return "user/settings";
    }
}
