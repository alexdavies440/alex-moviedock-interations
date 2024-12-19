package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.moviedock.models.User;
import org.launchcode.moviedock.models.data.UserRepository;
import org.launchcode.moviedock.models.dto.SigninFormDTO;
import org.launchcode.moviedock.models.dto.SignupFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {

        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    // Determines whether a user is currently signed in and displays the appropriate option to sign in or out
    public String getOption(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = this.getUserFromSession(session);

        if (user == null) {
            return "SIGN IN";
        } else {
            return "SIGN OUT";
        }
    }

    // Determines whether a user is currently signed in and assigns the appropriate path for signin/signout button
    public String getPath(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = this.getUserFromSession(session);

        if (user == null) {
            return "/signin";
        } else {
            return "/signout";
        }
    }

    @GetMapping("/signin")
    public String displaySigninForm(Model model, HttpServletRequest request) {
        model.addAttribute(new SigninFormDTO());
        model.addAttribute("title", "Sign In");
        model.addAttribute("option", getOption(request));
        model.addAttribute("path", getPath(request));
        return "user/signin";
    }

    @PostMapping("/signin")
    public String processSigninForm(@ModelAttribute @Valid SigninFormDTO signinFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Sign In");
            return "user/signin";
        }

        User theUser = userRepository.findByUsername(signinFormDTO.getUsername());
        model.addAttribute("user", theUser);

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("title", "Sign In");
            return "user/signin";
        }

        String password = signinFormDTO.getPassword();

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Sign In");
            return "user/signin";
        }

        setUserInSession(request.getSession(), theUser);

        model.addAttribute("option", getOption(request));
        model.addAttribute("path", getPath(request));

        return "redirect:user/profile";
    }

    @GetMapping("/signup")
    public String displaySignupForm(Model model, HttpServletRequest request) {

        model.addAttribute(new SignupFormDTO());
        model.addAttribute("title", "Sign Up");

        // In case you accidentally hit signup but wanted to sign in
        model.addAttribute("option", getOption(request));
        model.addAttribute("path", getPath(request));

        return "user/signup";
    }

    @PostMapping("/signup")
    public String processSignupForm(@ModelAttribute @Valid SignupFormDTO signupFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Sign Up");
            return "user/signup";
        }

        User existingUser = userRepository.findByUsername(signupFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "Sorry, someone has already taken that username. Please try another");
            model.addAttribute("title", "Sign Up");
            return "user/signup";
        }

        String password = signupFormDTO.getPassword();
        String verifyPassword = signupFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Please check that passwords match");
            model.addAttribute("title", "Sign Up");
            return "user/signup";
        }

        User newUser = new User(signupFormDTO.getUsername(), signupFormDTO.getEmail(), signupFormDTO.getPassword());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:/profile";
    }

    @GetMapping("/signout")
    public String signout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/user/signin";
    }

    @GetMapping("/delete")
    public String displayDeleteAccount(Model model, HttpServletRequest request) {

        model.addAttribute(new SigninFormDTO());
        model.addAttribute("title", "Delete Account");
        model.addAttribute("option", getOption(request));
        model.addAttribute("path", getPath(request));

        return "user/delete";
    }

    @PostMapping("/delete")
    public String nukeAccount(Model model, HttpServletRequest request,
                              @ModelAttribute @Valid SigninFormDTO signinFormDTO, Errors errors) {

        model.addAttribute("title", "Delete Account");
        model.addAttribute("option", getOption(request));
        model.addAttribute("path", getPath(request));

        if (errors.hasErrors()) {
            model.addAttribute("title", "Delete Account");
            return "user/delete";
        }

        User theUser = userRepository.findByUsername(signinFormDTO.getUsername());
        model.addAttribute("user", theUser);

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "Please try again");
            model.addAttribute("title", "Sign In");
            return "user/delete";
        }

        String password = signinFormDTO.getPassword();

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Delete Account");
            return "user/delete";
        }

        userRepository.delete(theUser);

        request.getSession().invalidate();
        return "redirect:/user/signin";
    }

    @GetMapping("/settings")
    public String displaySettings(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = this.getUserFromSession(session);

        model.addAttribute(new SignupFormDTO());
        model.addAttribute("user", user);
        model.addAttribute("title", "Account Settings");
        model.addAttribute("option", getOption(request));
        model.addAttribute("path", getPath(request));


        return "user/settings";
    }

    @PostMapping("/settings")
        public String updateEmail(Model model, HttpServletRequest request,
                                  @ModelAttribute @Valid SignupFormDTO signupFormDTO, Errors errors) {

        HttpSession session = request.getSession();
        User user = this.getUserFromSession(session);

        model.addAttribute("user", user);
        model.addAttribute("title", "Account Settings");
        model.addAttribute("option", getOption(request));
        model.addAttribute("path", getPath(request));

        if (errors.hasErrors()) {

            model.addAttribute("title", "Account Settings");
            model.addAttribute("option", getOption(request));
            model.addAttribute("path", getPath(request));

            return "user/settings";
        }

        user.setEmail(signupFormDTO.getEmail());

        return "user/settings";

    }
}
