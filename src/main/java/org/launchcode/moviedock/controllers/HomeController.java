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


    @GetMapping("/home")
    public String displaySigninSignout(Model model, HttpServletRequest request, String option, String path) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if (user == null) {
            option = "SIGN IN";
            path = "/signin";
        } else {
            option = "SIGN OUT";
            path = "/signout";
        }

        model.addAttribute("option", option);
        model.addAttribute("path", path);
        model.addAttribute("title", "Welcome");


        return "index";
    }

    @GetMapping("/profile")
    public String displayMyProfile(Model model, HttpServletRequest request, String option, String path) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("user", user);

        if (user == null) {
            option = "SIGN IN";
            path = "/signin";
        } else {
            option = "SIGN OUT";
            path = "/signout";
        }

        model.addAttribute("option", option);
        model.addAttribute("path", path);
        model.addAttribute("title", "Welcome");

        return "profile";
    }

    @GetMapping("/profile/{username}")
    public String displayUserProfile(Model model, @PathVariable String username, HttpServletRequest request, String option, String path) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("user", user);

        if (user == null) {
            option = "SIGN IN";
            path = "/signin";
        } else {
            option = "SIGN OUT";
            path = "/signout";
        }

        model.addAttribute("option", option);
        model.addAttribute("path", path);
        model.addAttribute("title", "Welcome");

        Optional<User> optUser = Optional.ofNullable(userRepository.findByUsername(username));

        if(optUser.isPresent()) {
            User aUser = (User) optUser.get();
            model.addAttribute("user", aUser);
            return "profile";
        } else {
            return "redirect:";
        }
    }



}
