package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.moviedock.models.dto.SignupFormDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;


@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSuccess() {return "user/profile"; }

    @GetMapping("signup")
    public String showRegistrationForm(WebRequest request, Model model) {
        SignupFormDTO signupFormDTO = new SignupFormDTO();
        model.addAttribute("user", signupFormDTO);
        return "registration";
    }
}
