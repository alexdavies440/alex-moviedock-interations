package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.dto.SignupFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private AppUserRepository appUserRepository;

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
        String userRole = "USER";

        AppUser newAppUser = new AppUser(signupFormDTO.getUsername(), signupFormDTO.getEmail(), userPassword, userRole);
        appUserRepository.save(newAppUser);

        return "index";

    }


}
