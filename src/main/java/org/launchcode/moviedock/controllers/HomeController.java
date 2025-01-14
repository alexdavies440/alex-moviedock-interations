package org.launchcode.moviedock.controllers;

import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.security.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    private PrincipalService principalService;

    @GetMapping("/")
    public String home() {
        return "index";
    }


    @GetMapping("/profile")
    public String myProfile(Model model) {
        AppUser user = principalService.getPrincipal().get();
        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/profile/{username}")
    public String viewProfile(@PathVariable String username, Model model) {

        Optional<AppUser> appUser = appUserRepository.findByUsername(username);

        if(appUser.isPresent()) {
            AppUser user = (AppUser) appUser.get();
            model.addAttribute("user", user);
            return "user/profile";
        } else {
            return "redirect:..";
        }
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/test")
    public String testPage() {
        return "theme-test";
    }
}
