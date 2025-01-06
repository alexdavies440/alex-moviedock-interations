package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.dto.EmailDto;
import org.launchcode.moviedock.service.PrincipalService;
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
    private AppUserRepository appUserRepository;

    @Autowired
    private PrincipalService principalService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/settings")
    public String settings(Model model) {

        String username = principalService.getAuthentication().getName();
        Optional<AppUser> principal = appUserRepository.findByUsername(username);

        if (principal.isPresent()) {
            model.addAttribute("email", principal.get().getEmail());
            return "user/settings";
        }

        return "redirect:..";
    }

    @GetMapping("/settings/delete-account")
    public String deleteAccount(Model model) {

        return "user/delete-account";
    }

    @PostMapping("/settings/delete-account")
    public String deleteAccountSuccess(@RequestParam String providedPassword, Model model, HttpServletRequest request)
    {

        String username = principalService.getAuthentication().getName();

        Optional<AppUser> principal = appUserRepository.findByUsername(username);

        if (principal.isPresent()) {
            String userPassword = principal.get().getPassword();

            model.addAttribute("error", true);

            if (!passwordEncoder.matches(providedPassword, userPassword)) {
                return "user/delete-account";
            }

            AppUser exUser = (AppUser) principal.get();
            appUserRepository.delete(exUser);
            request.getSession().invalidate();

            return "redirect:/signin";
        }
        return "redirect:..";
    }

    @GetMapping("/settings/update-email")
    public String updateEmail(Model model) {

        model.addAttribute(new EmailDto());

        return "user/update-email";
    }

    @PostMapping("/settings/update-email")
    public String updateEmailSuccess(@Valid @ModelAttribute EmailDto emailDto, Errors errors) {

        if (errors.hasErrors()) {
            return "user/update-email";
        }

        String username = principalService.getAuthentication().getName();

        Optional<AppUser> principal = appUserRepository.findByUsername(username);

        if (principal.isPresent()) {
            principal.get().setEmail(emailDto.getEmail());
            appUserRepository.save(principal.get());

            return "redirect:/settings";
        }
        return "redirect:..";
    }
}
