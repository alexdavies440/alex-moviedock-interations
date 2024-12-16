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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("/profile/{username}")
    public String displayUserProfile(Model model, @PathVariable String username) {
        Optional<User> optUser = Optional.ofNullable(userRepository.findByUsername(username));

        if(optUser.isPresent()) {
            User user = (User) optUser.get();
            model.addAttribute("user", user);
            return "profile";
        } else {
            return "redirect:";
        }
    }



}
