package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.Theme;
import org.launchcode.moviedock.models.dto.ChangePasswordDto;
import org.launchcode.moviedock.models.dto.EmailDto;
import org.launchcode.moviedock.security.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SettingsController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PrincipalService principalService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/settings/delete-account")
    public String deleteAccount(Model model) {

        return "user/delete-account";
    }

    @PostMapping("/settings/delete-account")
    public String deleteAccountSuccess(@RequestParam String providedPassword, Model model, HttpServletRequest request)
    {

        AppUser principal = principalService.getPrincipal();

            String userPassword = principal.getPassword();

            model.addAttribute("error", true);

            if (!passwordEncoder.matches(providedPassword, userPassword)) {
                return "user/delete-account";
            }

            appUserRepository.delete(principal);
            request.getSession().invalidate();

        return "redirect:..";
    }

    @GetMapping("/settings/update-email")
    public String updateEmail(Model model) {

        AppUser principal = principalService.getPrincipal();

        model.addAttribute("email", principal.getEmail());
        model.addAttribute(new EmailDto());

        return "user/update-email";
    }

    @PostMapping("/settings/update-email")
    public String updateEmailSuccess(@Valid @ModelAttribute EmailDto emailDto, Errors errors, Model model) {

        AppUser principal = principalService.getPrincipal();
        model.addAttribute("email", principal.getEmail());

        if (errors.hasErrors()) {
            model.addAttribute("email", principal.getEmail());
            return "user/update-email";
        }

        principal.setEmail(emailDto.getEmail());
        appUserRepository.save(principal);

        return "redirect:/settings/update-email";
    }

    @GetMapping("/settings/change-theme")
    public String displayThemeSettings(Model model) {

        AppUser principal = principalService.getPrincipal();

        boolean isDark = principal.getTheme().equals(Theme.DARK);
        model.addAttribute("isDark", isDark);

        boolean isLight = principal.getTheme().equals(Theme.LIGHT);
        model.addAttribute("isLight", isLight);

        boolean isSeafoam = principal.getTheme().equals(Theme.SEAFOAM);
        model.addAttribute("isSeafoam", isSeafoam);

        boolean isSlate = principal.getTheme().equals(Theme.SLATE);
        model.addAttribute("isSlate", isSlate);

        return "user/change-theme";
    }

    @PostMapping("/settings/change-theme")
    public String changeTheme(@RequestParam Theme theme) {

        AppUser principal = principalService.getPrincipal();

        principal.setTheme(theme);
        appUserRepository.save(principal);

        return "redirect:/settings/change-theme";
    }

    @GetMapping("/settings/change-password")
    public String changePasswordPage(Model model) {
        model.addAttribute(new ChangePasswordDto());
        return "user/change-password";
    }

    @PostMapping("/settings/change-password")
    public String changePassword(@ModelAttribute @Valid ChangePasswordDto changePasswordDto, Errors errors, Model model) {

        AppUser principal = principalService.getPrincipal();

        if (errors.hasErrors()) {
            return "user/change-password";
        }

        String newPassword = changePasswordDto.getNewPassword();
        String verifyNewPassword = changePasswordDto.getVerifyNewPassword();

        if (!newPassword.equals(verifyNewPassword)) {
            errors.rejectValue(
                    "verifyNewPassword",
                    "passwords.mismatch",
                    "Please check that passwords match");

            return "user/change-password";
        }

        String currentPassword = changePasswordDto.getCurrentPassword();

        if (newPassword.equals(currentPassword)) {
            errors.rejectValue(
                    "newPassword",
                    "password.notNew",
                    "New password should be different from current password");
            return "user/change-password";
        }

        if (!passwordEncoder.matches(currentPassword, principal.getPassword())) {
            errors.rejectValue(
                    "currentPassword",
                    "password.incorrect",
                    "Please check current password");
            return "user/change-password";
        }

        principal.setPassword(passwordEncoder.encode(newPassword));
        appUserRepository.save(principal);

        model.addAttribute("greeting", "Your password has been changed successfully");

        return "user/change-password";
    }
}
