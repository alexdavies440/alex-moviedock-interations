package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.moviedock.models.User;
import org.launchcode.moviedock.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    // The following two methods are necessary for the SIGN IN/SIGN OUT button to work properly.
    // I will probably revamp this over time

    // Determines whether a user is currently signed in and displays the appropriate option to sign in or out
    public String getOption(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if (user == null) {
            return "SIGN IN";
        } else {
            return "SIGN OUT";
        }
    }

    // Determines whether a user is currently signed in and assigns the appropriate path for signin/signout button
    public String getPath(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if (user == null) {
            return "/signin";
        } else {
            return "/signout";
        }
    }

    @GetMapping("/home")
    public String displaySigninSignoutOption(Model model, HttpServletRequest request) {

        model.addAttribute("option", getOption(request));
        model.addAttribute("path", getPath(request));
        model.addAttribute("title", "Welcome");

        return "index";
    }

    @GetMapping("/search")
    public String searchPage(Model model, HttpServletRequest request) {

        model.addAttribute("option", getOption(request));
        model.addAttribute("path", getPath(request));

        return "search";
    }

    @GetMapping("/profile")
    public String displayCurrentUserProfile(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("user", user);
        model.addAttribute("option", getOption(request));
        model.addAttribute("path", getPath(request));
        model.addAttribute("title", "Welcome");

        return "user/profile";
    }

    @GetMapping("/profile/{username}")
    public String displayUserProfile(Model model, @PathVariable String username, HttpServletRequest request) {

        model.addAttribute("option", getOption(request));
        model.addAttribute("path", getPath(request));
        model.addAttribute("title", "Welcome");

        Optional<User> optUser = Optional.ofNullable(userRepository.findByUsername(username));

        if(optUser.isPresent()) {
            User aUser = (User) optUser.get();
            model.addAttribute("user", aUser);
            return "user/profile";
        } else {
            return "redirect:..";
        }
    }

}
