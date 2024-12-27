package org.launchcode.moviedock.controllers;

import com.mysql.cj.protocol.x.XAuthenticationProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.dto.UpdateEmailDTO;
import org.launchcode.moviedock.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public String settings(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        String username = userDetails.getUsername();
        Optional<AppUser> principal = appUserRepository.findByUsername(username);
        model.addAttribute("email", principal.get().getEmail());

        return "profile/settings";
    }

    @GetMapping("/settings/delete-account")
    public String deleteAccount(Model model) {

        return "profile/delete-account";
    }

    @PostMapping("/settings/delete-account")
    public String deleteAccountSuccess(@RequestParam String password, Model model,
                                       @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {


        Optional<AppUser> principal = appUserRepository.findByUsername(userDetails.getUsername());


        String userPassword = principal.get().getPassword();

        model.addAttribute("password", password);

        if (!passwordEncoder.matches(password, userPassword)) {
            return "profile/delete-account";
        }

        AppUser exUser = (AppUser) principal.get();
        appUserRepository.delete(exUser);
        request.getSession().invalidate();

        return "redirect:/signin";
    }

    @GetMapping("/settings/update-email")
    public String updateEmail(Model model) {

        model.addAttribute(new UpdateEmailDTO());

        return "profile/update-email";
    }

    @PostMapping("/settings/update-email")
    public String updateEmailSuccess(@ModelAttribute @Valid UpdateEmailDTO updateEmailDTO,
                                     @AuthenticationPrincipal UserDetails userDetails, Model model) {

        Optional<AppUser> principal = appUserRepository.findByUsername(userDetails.getUsername());

        principal.get().setEmail(updateEmailDTO.getEmail());
        appUserRepository.save(principal.get());

        return "redirect:/settings";
    }
}
