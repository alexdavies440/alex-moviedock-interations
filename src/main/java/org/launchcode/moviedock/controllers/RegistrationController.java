package org.launchcode.moviedock.controllers;

import jakarta.servlet.ServletException;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.dto.AppUserDto;
import org.launchcode.moviedock.models.dto.VerifyCodeDto;
import org.launchcode.moviedock.models.themes.Mode;
import org.launchcode.moviedock.security.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

import static org.thymeleaf.util.StringUtils.randomAlphanumeric;

@Controller
public class RegistrationController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;



    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute(new AppUserDto());
        return "/user/signup";
    }

    @PostMapping("/signup")
    public String signupSuccess(Model model, @ModelAttribute @Valid AppUserDto appUserDto,
                                Errors errors) throws ServletException {

        if (errors.hasErrors()) {
            return "user/signup";
        }

        Optional<AppUser> existingUser = appUserRepository.findByUsername(appUserDto.getUsername());

        if (existingUser.isPresent()) {
            errors.rejectValue(
                    "username",
                    "username.alreadyexists",
                    "Sorry, someone has already taken that username. Please try another");
            return "user/signup";
        }

        String password = appUserDto.getPassword();
        String verifyPassword = appUserDto.getVerifyPassword();

        if (!password.equals(verifyPassword)) {
            errors.rejectValue(
                    "password",
                    "passwords.mismatch",
                    "Please check that passwords match");
            return "user/signup";
        }

        password = passwordEncoder.encode(password);
        String role = "USER";

        // Account will not be enabled until email is verified
        boolean isEnabled = false;

        String verificationCode = randomAlphanumeric(6);

        AppUser newUser = new AppUser(
                appUserDto.getUsername(),
                appUserDto.getEmail(),
                password,
                role,
                isEnabled,
                verificationCode,
                Mode.DARK);
        appUserRepository.save(newUser);

        emailService.sendEmail(
                "Thank you for joining Moviedock!",
                appUserDto.getEmail(),
                "To complete sign up for "
                        + newUser.getUsername()
                        + ", please verify your account with this code: "
                        + verificationCode);

        return "redirect:/signup-verify";
    }

    @GetMapping("/signup-verify")
    public String verifyNewUserEmail(Model model) {

        model.addAttribute(new VerifyCodeDto());

            return "user/verify-email";
        }

        @PostMapping("/signup-verify")
    public String verifySuccess(@ModelAttribute VerifyCodeDto verifyCodeDto, Model model) {

            Optional<AppUser> newUser = appUserRepository.findByVerificationCode(verifyCodeDto.getCode());

            // If code corresponds to existing user, set user to enabled
            if (newUser.isPresent()) {
                AppUser userObj = newUser.get();
                userObj.setEnabled(true);
                userObj.setVerificationCode(null);
                appUserRepository.save(userObj);
            }

            model.addAttribute("greeting", "You can now sign into your new account for the first time");

            return "user/signin";
        }
}
