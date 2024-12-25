package org.launchcode.moviedock.controllers;

import jakarta.validation.Valid;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.dto.SigninFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute(new SigninFormDTO());
        return "profile/signin";
    }

    @PostMapping("/signin")
    public String signinSuccess(Model model, @ModelAttribute @Valid SigninFormDTO signinFormDTO,
                                Errors errors) {

        if (errors.hasErrors()) {
            return "profile/signin";
        }

        Optional<AppUser> theUser = appUserRepository.findByUsername(signinFormDTO.getUsername());

        if (theUser.isEmpty()) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            return "profile/signin";
        }


        String userPassword = theUser.get().getPassword();
        String providedPassword = passwordEncoder.encode(signinFormDTO.getPassword());

        if (!providedPassword.equals(userPassword)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Sign In");
            return "user/signin";
        }


        return "profile/profile-page";
    }

}
