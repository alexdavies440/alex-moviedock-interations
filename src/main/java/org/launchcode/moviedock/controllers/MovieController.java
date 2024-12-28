package org.launchcode.moviedock.controllers;

import jakarta.validation.Valid;
import org.launchcode.moviedock.data.MovieRepository;
import org.launchcode.moviedock.data.UserRepository;
import org.launchcode.moviedock.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/movies")
    public String displayMovies(Model model) {
        model.addAttribute("title", "All Movies");
        model.addAttribute("movies", movieRepository.findAll());
        return "movies/index";
    }


    @GetMapping("/movies/create-movies")
    public String displayCreateMovieForm(Model model) {
        model.addAttribute("title", "Create Favorite Movie");
        model.addAttribute("movie", new Movie());
        return "/movies/create-movies";
    }

    @PostMapping("/movies/create-movies")
    public String processCreateMovieForm(@ModelAttribute @Valid Movie movie, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Movie");
            model.addAttribute("movie", movie);
            return "/movies/create-movies";
        }
        movieRepository.save(movie);
        return "redirect:/movies";
    }
}
