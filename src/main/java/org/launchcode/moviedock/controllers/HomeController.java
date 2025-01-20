package org.launchcode.moviedock.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.ApiMovieRepository;
import org.launchcode.moviedock.data.AppUserRepository;
import org.launchcode.moviedock.models.ApiMovie;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.security.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ApiMovieRepository apiMovieRepository;

    @Autowired
    private PrincipalService principalService;

    @GetMapping("/")
    public String home(Model model) throws JsonProcessingException {

        //returns apiID of movies by view count
        //the number of movies displayed can be changed in the query in ApiMovieRepository
        List<String> listOfApiIds = apiMovieRepository.getTopMovies();
        System.out.println(listOfApiIds);
        ApiMovie[] movies = new ApiMovie[listOfApiIds.size()];
        //
        for (int i = 0; i < movies.length; i++){
            ApiMovie apiMovie1 = new ApiMovie();
            apiMovie1.setMovieInfoById(listOfApiIds.get(i));

            movies[i] = apiMovie1;


            String year = apiMovie1.getYear();
            String title = apiMovie1.getTitle();
            String apiId = apiMovie1.getApiID();
            System.out.println(title);
            System.out.println(i);

        }



        if(movies[0]!=null) {
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
    public String displayViewMovie(Model model, @PathVariable String apiId, @ModelAttribute @Valid ApiMovie apiMovie) throws JsonProcessingException{



        apiMovie.setMovieInfoById(apiId);

        String year = apiMovie.getYear();
        String title = apiMovie.getTitle();
        String plot = apiMovie.getPlot();
        String director = apiMovie.getDirector();
        String poster = apiMovie.getPoster();

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
