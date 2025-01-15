package org.launchcode.moviedock.controllers;

import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.themes.Mode;
import org.launchcode.moviedock.models.themes.Theme;
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
    public Theme setTheme(Mode theMode) {

        String username = principalService.getAuthentication().getName();
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);

        if (appUser.isPresent()) {
            theMode = appUser.get().getMode();
        } else {
            theMode = Mode.DARK;
        }
        return new Theme(theMode);
    }

    @ModelAttribute("checked")
    public Mode mode() {
        String username = principalService.getAuthentication().getName();
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);

        return appUser.get().getMode();
    }
}
