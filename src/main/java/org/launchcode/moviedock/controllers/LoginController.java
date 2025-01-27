package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.dto.VerifyCodeDto;
import org.launchcode.moviedock.security.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static org.thymeleaf.util.StringUtils.randomAlphanumeric;

@Controller
public class LoginController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("title", "Please Sign In");
        model.addAttribute("greeting", "Please Sign In");

        return "user/signin";
    }

    @GetMapping("/signout")
    public String signout(HttpServletRequest request, Model model){
        model.addAttribute("greeting", "You have been signed out");
        request.getSession().invalidate();
        return "redirect:/signin";
    }

    @GetMapping("/reset-password")
    public String resetPassword() {
        return "user/reset-password";
    }

    @PostMapping("/reset-password")
    public String sendNewPassword(@RequestParam String username, Model model) {

        Optional<AppUser> appUser = appUserRepository.findByUsername(username);

        if (appUser.isEmpty()) {
            model.addAttribute("error", true);
            return "user/reset-password";
        }

        AppUser user = appUser.get();
        String verificationCode = randomAlphanumeric(15);
        appUser.get().setVerificationCode(verificationCode);
        appUserRepository.save(appUser.get());

        emailService.sendEmail(
                "Password Reset",
                user.getEmail(),
                "Here is the new password for "
                        + user.getUsername()
                        + ": "
                        + verificationCode
                        + ". If you did not initiate a password reset, you can ignore this email.");

        return "redirect:/reset-password/verify";
    }

    @GetMapping("/reset-password/verify")
    public String verifyEmailCode(Model model) {
        model.addAttribute(new VerifyCodeDto());
        return "user/verify-email-password";
    }

    @PostMapping("/reset-password/verify")
    public String setNewPassword(@ModelAttribute @Valid VerifyCodeDto verifyCodeDto, Model model) {

        Optional<AppUser> appUser = appUserRepository.findByVerificationCode(verifyCodeDto.getCode());

        if (appUser.isPresent()) {
            AppUser userObj = appUser.get();
            userObj.setPassword(passwordEncoder.encode(userObj.getVerificationCode()));
            userObj.setVerificationCode(null);
            appUserRepository.save(userObj);
        }

        model.addAttribute("greeting", "You can now sign into your account with the password we emailed you");

        return "user/signin";
    }

}
