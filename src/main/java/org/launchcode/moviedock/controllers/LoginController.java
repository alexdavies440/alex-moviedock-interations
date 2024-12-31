package org.launchcode.moviedock.controllers;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.moviedock.data.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("title", "Please Sign In");
        return "profile/signin";
    }

    @GetMapping("/signout")
    public String signout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/signin";
    }

}
