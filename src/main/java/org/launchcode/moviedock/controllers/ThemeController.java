package org.launchcode.moviedock.controllers;

import org.launchcode.moviedock.models.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ThemeController {


    @GetMapping("/test")
    public String home(Model model) {

//        Theme theme = new Theme("light");
//
//        model.addAttribute("cssPath", theme.getCssPath());
//        model.addAttribute("navMode", theme.getNavMode());

        return "theme-test";
    }
}
