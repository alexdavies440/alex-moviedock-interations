package org.launchcode.moviedock.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.ApiMovieRepository;
import org.launchcode.moviedock.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.launchcode.moviedock.models.dto.MovieHelper;

import java.util.Optional;




@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private ApiMovieRepository apiMovieRepository;

    @GetMapping("results")
    public String index(Model model){
        //model.addAttribute("title", "search for a movie");
        return "search";
    }


    @PostMapping("results")
    public String processSearchResults(@ModelAttribute @Valid ApiMovie apiMovie, Model model, @RequestParam String searchTerm) throws JsonProcessingException {

        //model.addAttribute("title", "search for a movie");

        //get list of api ids
        MovieHelper mh = new MovieHelper();
        String[] listOfApiIds = mh.makeMovieList(searchTerm);
        ApiMovie[] movies;
        movies = new ApiMovie[10];

        model.addAttribute("apis", listOfApiIds);

        for (int i = 0; i < listOfApiIds.length; i++){
            ApiMovie apiMovie1 = new ApiMovie();
            apiMovie1.setMovieInfoById(listOfApiIds[i]);
            movies[i] = apiMovie1;


            String year = apiMovie1.getYear();
            String title = apiMovie1.getTitle();
            String apiId = apiMovie1.getApiID();
            System.out.println(title);

            /*model.addAttribute("title", title);
            model.addAttribute("year", year);
            model.addAttribute("apiId", apiId);*/

        }

        model.addAttribute("movies",movies);



        apiMovie.setMovieInfoByName(searchTerm);
        String year = apiMovie.getYear();
        String title = apiMovie.getTitle();
        String apiId = apiMovie.getApiID();
        String plot = apiMovie.getPlot();




        model.addAttribute("title", title);
        model.addAttribute("year", year);
        model.addAttribute("apiId", apiId);


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


        Optional<ApiMovie> optApiMovie = apiMovieRepository.findById(apiMovie.getId());
        if (optApiMovie.isPresent()) {
            ApiMovie a = (ApiMovie) optApiMovie.get();
            a.userView();
            apiMovieRepository.save(a);
        }
        else{
            apiMovieRepository.save(apiMovie);
        }




        return "movie-view";
    }





}