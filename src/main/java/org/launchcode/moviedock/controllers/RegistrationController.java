package org.launchcode.moviedock.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.dto.AppUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@RestController
@Controller
public class RegistrationController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute(new AppUserDto());
        return "/profile/signup";
    }

    @PostMapping("/signup")
    public String signupSuccess(Model model, @ModelAttribute @Valid AppUserDto appUserDto,
                                Errors errors, HttpServletRequest request) throws ServletException {

        if (errors.hasErrors()) {
            return "profile/signup";
        }

        Optional<AppUser> existingUser = appUserRepository.findByUsername(appUserDto.getUsername());

        if (existingUser.isPresent()) {
            errors.rejectValue("username", "username.alreadyexists", "Sorry, someone has already taken that username. Please try another");
            return "profile/signup";
        }

        String password = appUserDto.getPassword();

        String verifyPassword = appUserDto.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Please check that passwords match");
            return "profile/signup";
        }

        password = passwordEncoder.encode(password);
        String role = "USER";

        AppUser newUser = new AppUser(appUserDto.getUsername(), appUserDto.getEmail(), password, role);
        appUserRepository.save(newUser);

        request.login(newUser.getUsername(), appUserDto.getPassword());

        return "profile/profile-page";
    }
}
