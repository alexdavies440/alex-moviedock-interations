package org.launchcode.moviedock.controller;

import org.launchcode.moviedock.data.MovieRepository;
import org.launchcode.moviedock.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movies")
    public List<Movie> displayMovies() {
        return (List<Movie>) movieRepository.findAll();
    }
}
