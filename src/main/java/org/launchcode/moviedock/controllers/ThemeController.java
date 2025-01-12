package org.launchcode.moviedock.controllers;

import org.launchcode.moviedock.models.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class ThemeController {


    @ModelAttribute("theme")
    public Theme setTheme() {

        return new Theme("light");

    }
}
