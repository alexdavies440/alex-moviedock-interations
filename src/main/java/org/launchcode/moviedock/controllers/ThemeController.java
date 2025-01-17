package org.launchcode.moviedock.controllers;

import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.Theme;
import org.launchcode.moviedock.security.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;


@ControllerAdvice
public class ThemeController {

    @Autowired
    private PrincipalService principalService;

    @Autowired
    private AppUserRepository appUserRepository;



    // Global modelattribute theme based on user logged in. Default is dark
    @ModelAttribute("theme")
    public Theme globalTheme() {

        String username = principalService.getAuthentication().getName();
        Optional<AppUser> principal = appUserRepository.findByUsername(username);

        if (principal.isPresent()) {
            return principal.get().getTheme();
        } else {
            return Theme.DARK;
        }
    }

}
