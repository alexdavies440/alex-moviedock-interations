package org.launchcode.moviedock.controllers;

import org.launchcode.moviedock.data.ProfileRepository;
import org.launchcode.moviedock.models.dto.SigninFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signin")
    public String signin(Model model) {

        model.addAttribute(new SigninFormDTO());
        return "profile/signin";
    }
}
