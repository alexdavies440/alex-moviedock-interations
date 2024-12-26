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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class SettingsController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/settings")
    public String settings() {
        return "profile/settings";
    }

    @GetMapping("/settings/delete-account")
    public String deleteAccount(Model model) {
        model.addAttribute(new SigninFormDTO());

        return "profile/delete-account";
    }

    @PostMapping("/settings/delete-account")
    public String deleteAccountSuccess(@ModelAttribute @Valid SigninFormDTO signinFormDTO, Errors errors) {

        if (errors.hasErrors()) {
            return "profile/delete-account";
        }

        Optional<AppUser> optUser = appUserRepository.findByUsername(signinFormDTO.getUsername());

        if (optUser.isPresent()) {

            String providedPassword = signinFormDTO.getPassword();
            String password = optUser.get().getPassword();
            if (!password.equals(passwordEncoder.encode(providedPassword))) {
                errors.rejectValue("password",
                        "password.invalid",
                        "Invalid password");

                return "profile/delete-account";
            }

            AppUser exUser = (AppUser) optUser.get();
            appUserRepository.delete(exUser);
        }
        return "index";
    }
}
