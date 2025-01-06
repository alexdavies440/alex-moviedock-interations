package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("title", "Please Sign In");
        model.addAttribute("greeting", "Please Sign In");

        return "user/signin";
    }

    @GetMapping("/signout")
    public String signout(HttpServletRequest request, Model model){
        model.addAttribute("greeting", "You have been signed out");
        request.getSession().invalidate();
        return "redirect:/signin";
    }

}
