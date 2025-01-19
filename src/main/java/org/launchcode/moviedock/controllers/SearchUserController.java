package org.launchcode.moviedock.controllers;


import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Optional;

@Controller
public class SearchUserController {

    @Autowired
    AppUserRepository appUserRepository;


    @GetMapping("user_search")
    public String userSearch() {

        return "userSearch";
    }

    @PostMapping("user_search")
    public String listSearchResults(Model model, @RequestParam String searchName) {

        if (searchName.isEmpty()) {
            model.addAttribute("blankField", "Please Enter a name to search");
            return "userSearch";
        } else {

            List<AppUser> usersList = appUserRepository.findByUsernameLike(searchName);

            if (usersList.isEmpty()) {
                model.addAttribute("noMatch", "No user found with name '" + searchName + "'");
                return "userSearch";

            } else {
                model.addAttribute("usersList", usersList);
                return "userSearch";
            }
        }

    }

    @GetMapping("searched_profile")
    public String userSearchFound(Model model, @RequestParam int userId) {

        Optional<AppUser> appUser = appUserRepository.findById(userId);
        AppUser userFound = appUser.get();
        model.addAttribute("user",userFound);
        return "user/profile";
    }


}