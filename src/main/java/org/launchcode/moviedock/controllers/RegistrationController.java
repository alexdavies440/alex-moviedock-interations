package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.ProfileRepository;
import org.launchcode.moviedock.models.Profile;
import org.launchcode.moviedock.models.dto.SignupFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class RegistrationController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signupPage(Model model) {

        model.addAttribute(new SignupFormDTO());
        return "profile/signup";
    }


    @PostMapping("/signup")
    public String signupSuccess(@ModelAttribute @Valid SignupFormDTO signupFormDTO,
                                Errors errors, HttpServletRequest request,
                                Model model) {


        String userPassword = passwordEncoder.encode(signupFormDTO.getPassword());

        Profile newProfile = new Profile(signupFormDTO.getUsername(), signupFormDTO.getEmail(), userPassword);
        profileRepository.save(newProfile);

        return "index";

    }


}
