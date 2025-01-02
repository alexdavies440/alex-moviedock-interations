package org.launchcode.moviedock.controllers;

import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }


    @GetMapping("/profile")
    public String myProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        Optional<AppUser> principal = appUserRepository.findByUsername(userDetails.getUsername());

        model.addAttribute("user", principal.get());
        return "profile/profile-page";
    }

    @GetMapping("/profile/{username}")
    public String viewProfile(@PathVariable String username, Model model) {

        Optional<AppUser> appUser = appUserRepository.findByUsername(username);

        if(appUser.isPresent()) {
            AppUser aUser = (AppUser) appUser.get();
            model.addAttribute("user", aUser);
            return "profile/profile-page";
        } else {
            return "redirect:..";
        }
    }


    @GetMapping("/search")
    public String search() {
        return "search";
    }
}
