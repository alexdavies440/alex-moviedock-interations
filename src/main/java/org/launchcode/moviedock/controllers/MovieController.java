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



//    @GetMapping("/movies/add-favorite-movie")
@GetMapping("/movies/add-favorite-movie")
    public String displayAddFavoriteMovieForm(@RequestParam Integer movieId, Model model, HttpServletRequest request) {

        AppUser user = principalService.getPrincipal();
        Optional<Movie> optMovie = movieRepository.findById(movieId);

        if(optMovie.isPresent()){
            Movie movie = optMovie.get();
            UserMovieDTO userMovieDTO = new UserMovieDTO();
            userMovieDTO.setMovie(movie);
            userMovieDTO.setUser(user);
//        model.addAttribute("title", movie.getName() + " Details");
            model.addAttribute("movie", movie);
            model.addAttribute("userMovieDTO",userMovieDTO);
            model.addAttribute("alertMessage", "This movie is already added in Favorite Movies List");
            return "/movies/add-favorite-movie";
        }
        else {
            return "redirect:/profile";
        }

    }

//    @PostMapping("/movies/add-favorite-movie")
    @PostMapping("/movies/add-favorite-movie")
    public String processAddFavoriteMovieForm(@ModelAttribute @Valid UserMovieDTO userMovieDTO, Errors errors, Model model){
        Movie movie = userMovieDTO.getMovie();
        if (!errors.hasErrors()) {
            AppUser user = principalService.getPrincipal();
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

        AppUser user = principalService.getPrincipal();
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
            AppUser user = principalService.getPrincipal();

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
        AppUser user = principalService.getPrincipal();
        model.addAttribute("user",user);
        user.removeFavoriteMovie(movie.get());
        appUserRepository.save(user);
        return "user/profile";
    }

    @DeleteMapping("/movies/to-watch-movie/{movieId}")
    public String deleteMovieFromToWatchList(@PathVariable int movieId, Model model) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        AppUser user = principalService.getPrincipal();
        model.addAttribute("user",user);
        user.removeToWatchMovie(movie.get());
        appUserRepository.save(user);
        return "user/profile";
    }



}
