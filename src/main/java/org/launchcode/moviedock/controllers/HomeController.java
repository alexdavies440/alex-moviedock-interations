package org.launchcode.moviedock.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.data.MovieRepository;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.Movie;
import org.launchcode.moviedock.security.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    MovieRepository MovieRepository;

    @Autowired
    private PrincipalService principalService;

    @GetMapping("/")
    public String home(Model model) throws JsonProcessingException {

        //returns apiID of movies by view count
        //the number of movies displayed can be changed in the query in ApiMovieRepository

        List<String> listOfApiIds = new ArrayList<>();


        //if (MovieRepository.getTopMovies()!=null) {
            listOfApiIds = MovieRepository.getTopMovies();
        //}




        Movie[] movies = new Movie[listOfApiIds.size()];


        for (int i = 0; i < movies.length; i++){
            Movie Movie1 = new Movie();
            Movie1.setMovieInfoById(listOfApiIds.get(i));

            movies[i] = Movie1;


            String year = Movie1.getYear();
            String title = Movie1.getName();
            String apiId = Movie1.getApiID();
        }



        if (movies.length!=0) {
            model.addAttribute("movies", movies);
        }

        return "index";
    }


    @GetMapping("/profile")
    public String myProfile(Model model) {
        AppUser user = principalService.getPrincipal();
        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/profile/{username}")
    public String viewProfile(@PathVariable String username, Model model) {

        Optional<AppUser> appUser = appUserRepository.findByUsername(username);

        if(appUser.isPresent()) {
            AppUser user = (AppUser) appUser.get();
            model.addAttribute("user", user);
            return "user/profile";
        } else {
            return "redirect:..";
        }
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("movie-view/{apiId}")
    public String displayViewMovie(Model model, @PathVariable String apiId, @ModelAttribute @Valid Movie movie) throws JsonProcessingException{



        movie.setMovieInfoById(apiId);

        String year = movie.getYear();
        String title = movie.getName();
        String plot = movie.getPlot();
        String director = movie.getDirector();
        String poster = movie.getPoster();

        model.addAttribute("plot", plot);
        model.addAttribute("year", year);
        model.addAttribute("title", title);
        model.addAttribute("director", director);
        model.addAttribute("poster", poster);


        //commenting out so popular movies aren't made more popular from clicking on front page

        /*System.out.println(plot);
        if (plot!=null) {
            Optional<ApiMovie> optApiMovie = apiMovieRepository.findByApiID(apiMovie.getApiID());
            if (optApiMovie.isPresent()) {
                System.out.println(apiMovie.getApiID());
                ApiMovie a = (ApiMovie) optApiMovie.get();
                System.out.println("it exists");
                a.userView();
                apiMovieRepository.save(a);
            }
            else{
                System.out.println("it doesn't exist");
                apiMovie.userView();
                apiMovieRepository.save(apiMovie);
            }
        }*/
        return "movie-view";
    }


}
