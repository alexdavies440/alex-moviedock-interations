package org.launchcode.moviedock.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.dto.AppUserDto;
import org.launchcode.moviedock.models.dto.VerifyCodeDto;
import org.launchcode.moviedock.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import static org.thymeleaf.util.StringUtils.randomAlphanumeric;

//@RestController
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

        // Will come back to this with email verification
        boolean isEnabled = false;

        String verificationCode = randomAlphanumeric(6);

        AppUser newUser = new AppUser(appUserDto.getUsername(), appUserDto.getEmail(), password, role, isEnabled, verificationCode);
        appUserRepository.save(newUser);

        emailService.sendEmail(
                "Thank you for joining Moviedock!",
                appUserDto.getEmail(),
                "Please verify your account with this code " + verificationCode);

        return "redirect:/signup-verify";
    }

    @GetMapping("/signup-verify")
    public String verifyNewUserEmail(Model model) {

        model.addAttribute(new VerifyCodeDto());

            return "profile/verify-email";
        }

        @PostMapping("/signup-verify")
    public String verifySuccess(@ModelAttribute VerifyCodeDto verifyCodeDto) {

            Optional<AppUser> newUser = appUserRepository.findByVerificationCode(verifyCodeDto.getCode());

            // If code corresponds to existing user, set user to enabled
            if (newUser.isPresent()) {
                AppUser userObj = newUser.get();
                userObj.setEnabled(true);
                userObj.setVerificationCode(null);
                appUserRepository.save(userObj);
            }

            return "profile/signin";
        }
}
