package org.launchcode.moviedock.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.data.MovieRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.Movie;
import org.launchcode.moviedock.models.dto.UserMovieDTO;
import org.launchcode.moviedock.security.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
//@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PrincipalService principalService;


    //@GetMapping("/movies")
    @GetMapping("/movies")
    public String displayMovies(Model model) {
        model.addAttribute("title", "All Movies");
        model.addAttribute("movies", movieRepository.findAll());
        return "movies/index";
    }


//    @GetMapping("/movies/create-movies")
    @GetMapping("/movies/create-movies")
    public String displayCreateMovieForm(Model model) {
        model.addAttribute("title", "Create Favorite Movie");
        model.addAttribute("movie", new Movie());
        return "/movies/create-movies";
    }

//    @PostMapping("/movies/create-movies")
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

//    @GetMapping("/movies/movie-detail")
    @GetMapping("/movies/movie-detail")
    public String displayMovieDetailsForm(@RequestParam Integer movieId, Model model, HttpServletRequest request) {

        AppUser user = principalService.getPrincipal().get();
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

//    @PostMapping("/movies/movie-detail")
    @PostMapping("/movies/movie-detail")
    public String processMovieDetailsForm(@ModelAttribute @Valid UserMovieDTO userMovieDTO, Errors errors, HttpServletRequest request, Model model){
        Movie movie = userMovieDTO.getMovie();
        if (!errors.hasErrors()) {
            AppUser user = principalService.getPrincipal().get();
                //if(!user.getFavoriteMovies().contains(movie)){
                //user.getFavoriteMovies().add(movie);
                user.addFavoriteMovies(movie);
                user.addToWatchMovies(movie);
                appUserRepository.save(user);
                model.addAttribute("user", user);
                return "user/profile";
            }
        return "redirect:movies/movie-detail?" +movie.getId();
    }

//    @GetMapping("/movies/add-favorite-movie")
@GetMapping("/movies/add-favorite-movie")
    public String displayAddFavoriteMovieForm(@RequestParam Integer movieId, Model model, HttpServletRequest request) {

        AppUser user = principalService.getPrincipal().get();
        Optional<Movie> optMovie = movieRepository.findById(movieId);

        if(optMovie.isPresent()){
            Movie movie = optMovie.get();
            UserMovieDTO userMovieDTO = new UserMovieDTO();
            userMovieDTO.setMovie(movie);
            userMovieDTO.setUser(user);
//        model.addAttribute("title", movie.getName() + " Details");
            model.addAttribute("movie", movie);
            model.addAttribute("userMovieDTO",userMovieDTO);
            return "/movies/add-favorite-movie";
        }
        else {
            model.addAttribute("alertMessage", "This movie is already added in Favorite Movies List");
            System.out.println("movie is already added");
            return "redirect:/profile";
        }

    }

//    @PostMapping("/movies/add-favorite-movie")
    @PostMapping("/movies/add-favorite-movie")
    public String processAddFavoriteMovieForm(@ModelAttribute @Valid UserMovieDTO userMovieDTO, Errors errors, Model model){
        Movie movie = userMovieDTO.getMovie();
        if (!errors.hasErrors()) {
            AppUser user = principalService.getPrincipal().get();
            //if(!user.getFavoriteMovies().contains(movie)){
            //user.getFavoriteMovies().add(movie);
            user.addFavoriteMovies(movie);
            appUserRepository.save(user);
            model.addAttribute("user", user);
            return "user/profile";
        }
        return "redirect:movies/add-favorite-movie?" +movie.getId();
    }

//    @GetMapping("/movies/add-to-watch-movie")
    @GetMapping("/movies/add-to-watch-movie")
    public String displayToWatchMovieForm(@RequestParam Integer movieId, Model model) {

        AppUser user = principalService.getPrincipal().get();
        Optional<Movie> optMovie = movieRepository.findById(movieId);

        if(optMovie.isPresent()){
            Movie movie = optMovie.get();
            UserMovieDTO userMovieDTO = new UserMovieDTO();
            userMovieDTO.setMovie(movie);
            userMovieDTO.setUser(user);
//        model.addAttribute("title", movie.getName() + " Details");
            model.addAttribute("movie", movie);
            model.addAttribute("userMovieDTO",userMovieDTO);
            return "/movies/add-to-watch-movie";

        }else {
            model.addAttribute("alertMessage", "This movie is already added in To Watch Movies List");
            System.out.println("movie is already added");
            return "redirect:/profile";
        }

    }

//    @PostMapping("/movies/add-to-watch-movie")
    @PostMapping("/movies/add-to-watch-movie")
    public String processToWatchMovieForm(@ModelAttribute @Valid UserMovieDTO userMovieDTO, Errors errors, Model model){
        Movie movie = userMovieDTO.getMovie();
        if (!errors.hasErrors()) {
            AppUser user = principalService.getPrincipal().get();
            //if(!user.getFavoriteMovies().contains(movie)){
            //user.getFavoriteMovies().add(movie);
            user.addToWatchMovies(movie);
            appUserRepository.save(user);
            model.addAttribute("user", user);
            return "user/profile";
        }
        return "redirect:movies/add-to-watch-movie?" +movie.getId();
    }


    @DeleteMapping("/movies/{movieId}")
    public String deleteMovieFromFavoriteList(@PathVariable int movieId, Model model) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        AppUser user = principalService.getPrincipal().get();
        model.addAttribute("user",user);
        user.removeFavoriteMovie(movie.get());
        appUserRepository.save(user);
        return "user/profile";
    }

    @DeleteMapping("/movies/to-watch-movie/{movieId}")
    public String deleteMovieFromToWatchList(@PathVariable int movieId, Model model) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        AppUser user = principalService.getPrincipal().get();
        model.addAttribute("user",user);
        user.removeToWatchMovie(movie.get());
        appUserRepository.save(user);
        return "user/profile";
    }
}
