//package org.launchcode.moviedock.controllers;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import org.launchcode.moviedock.data.UserRepository;
//import org.launchcode.moviedock.models.User;
//import org.launchcode.moviedock.models.dto.SignupFormDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.context.request.WebRequest;
//
//import java.util.Optional;
//
//
//@Controller
//public class AuthController {
//
//    @Autowired
//    UserRepository userRepository;
//
//    private static final String userSessionKey = "user";
//
//    public User getUserFromSession(HttpSession session) {
//
//        Integer userId = (Integer) session.getAttribute(userSessionKey);
//        if (userId == null) {
//            return null;
//        }
//
//        Optional<User> user = userRepository.findById(userId);
//
//        if (user.isEmpty()) {
//            return null;
//        }
//
//        return user.get();
//    }
//
//    private static void setUserInSession(HttpSession session, User user) {
//        session.setAttribute(userSessionKey, user.getId());
//    }
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String loginSuccess() {return "user/profile"; }
//
//    @GetMapping("signup")
//    public String showRegistrationForm(Model model) {
//
//        model.addAttribute(new SignupFormDTO());
//        return "user/signup";
//    }
//
//    @PostMapping("/signup")
//    public String processSignupForm(@ModelAttribute @Valid SignupFormDTO signupFormDTO,
//                                    Errors errors, HttpServletRequest request,
//                                    Model model) {
//
//        if (errors.hasErrors()) {
//            model.addAttribute("title", "Sign Up");
//            return "user/signup";
//        }
//
//        Optional<User> existingUser = userRepository.findByUsername(signupFormDTO.getUsername());
//
//        if (existingUser.isPresent()) {
//            errors.rejectValue("username", "username.alreadyexists", "Sorry, someone has already taken that username. Please try another");
//            model.addAttribute("title", "Sign Up");
//            return "user/signup";
//        }
//
//        String password = signupFormDTO.getPassword();
//        String verifyPassword = signupFormDTO.getVerifyPassword();
//        if (!password.equals(verifyPassword)) {
//            errors.rejectValue("password", "passwords.mismatch", "Please check that passwords match");
//            model.addAttribute("title", "Sign Up");
//            return "user/signup";
//        }
//
//        User newUser = new User(signupFormDTO.getUsername(), signupFormDTO.getEmail(), signupFormDTO.getPassword() , "ROLE_USER");
//        userRepository.save(newUser);
//        setUserInSession(request.getSession(), newUser);
//
//        return "index";
//    }
//
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/settings")
//    public String settings() {
//        return "user/settings";
//    }
//}
