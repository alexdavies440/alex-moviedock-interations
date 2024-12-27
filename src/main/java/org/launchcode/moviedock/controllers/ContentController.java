package org.launchcode.moviedock.controllers;

import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class ContentController {

    @Autowired
    AppUserRepository appUserRepository;

    // Anything to do with CRUD for profiles such as lists, reviews, ratings

    @GetMapping("/placeholder-url")
    public String placeholderMethod(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        // Grabs currently logged in user(the principal) and searches the repository by username
        // From here you can create, update, and delete content for only the current user that is logged in
        Optional<AppUser> principal = appUserRepository.findByUsername(userDetails.getUsername());

        return "index";
    }
}
