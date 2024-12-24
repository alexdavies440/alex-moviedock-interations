package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/settings")
    public String settings() {
        return "profile/settings";
    }

    @GetMapping("/signout")
    public String signout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/user/signin";
    }
}
