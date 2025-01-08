package org.launchcode.moviedock.security.service;


import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class PrincipalService {

    @Autowired
    AppUserRepository appUserRepository;

    // Returns currently logged-in user from SecurityContext

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Optional<AppUser> getPrincipal() {
        String username = this.getAuthentication().getName();
        return appUserRepository.findByUsername(username);
    }
}
