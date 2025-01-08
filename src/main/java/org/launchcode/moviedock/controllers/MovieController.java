package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.MovieRepository;
import org.launchcode.moviedock.data.UserRepository;
import org.launchcode.moviedock.models.Movie;
import org.launchcode.moviedock.models.User;
import org.launchcode.moviedock.models.dto.UserMovieDTO;
import org.launchcode.moviedock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


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

    @GetMapping("/movies/movie-detail")
    public String displayMovieDetailsForm(@RequestParam Integer movieId, Model model, HttpServletRequest request) {

        User user = userService.getUserFromSession(request.getSession());
        Optional<Movie> optMovie = movieRepository.findById(movieId);
        Movie movie = optMovie.get();
        UserMovieDTO userMovieDTO = new UserMovieDTO();
        userMovieDTO.setMovie(movie);
        userMovieDTO.setUser(user);
        model.addAttribute("title", movie.getName() + " Details");
        model.addAttribute("movie", movie);
        model.addAttribute("userMovieDTO",userMovieDTO);
        return "/movies/movie-detail";
    }

    @PostMapping("/movies/movie-detail")
    public String processMovieDetailsForm(@ModelAttribute @Valid UserMovieDTO userMovieDTO, Errors errors, HttpServletRequest request, Model model){
        Movie movie = userMovieDTO.getMovie();
        if (!errors.hasErrors()) {
            User user = userService.getUserFromSession(request.getSession());
                //if(!user.getFavoriteMovies().contains(movie)){
                //user.getFavoriteMovies().add(movie);
                user.addFavoriteMovies(movie);
                user.addToWatchMovies(movie);
                userRepository.save(user);
                model.addAttribute("user", user);
                return "user/profile";
            }
        return "redirect:movies/movie-detail?" +movie.getId();
    }
}
