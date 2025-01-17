package org.launchcode.moviedock.models;

import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.security.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public enum Theme {
    DARK("/css/styles.css", "navbar navbar-inverse navbar-fixed-top bar"),
    LIGHT("/css/styles-light.css", "navbar navbar-default navbar-fixed-top bar"),
    ICE("/css/styles-ice.css", "navbar navbar-fixed-top bar"),
    SLATE("/css/styles-slate.css", "navbar navbar-fixed-top bar");

    private final String cssPath;
    private final String navMode;

    @Autowired
    private PrincipalService principalService;

    @Autowired
    private AppUserRepository appUserRepository;


    Theme(String cssPath, String navMode) {
        this.cssPath = cssPath;
        this.navMode = navMode;
    }

    public String getCssPath() {
        return cssPath;
    }

    public String getNavMode() {
        return navMode;
    }

    public boolean isChecked(Theme theme) {

        String username = principalService.getAuthentication().getName();
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);

        if (theme.equals(appUser.get().getTheme())) {
            return true;
        }
        return false;
    }
}
