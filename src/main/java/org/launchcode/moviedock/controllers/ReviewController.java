package org.launchcode.moviedock.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.moviedock.data.MovieRepository;
import org.launchcode.moviedock.data.ReviewRepository;
import org.launchcode.moviedock.data.UserRepository;
import org.launchcode.moviedock.models.Movie;
import org.launchcode.moviedock.models.Review;
import org.launchcode.moviedock.models.User;
import org.launchcode.moviedock.models.dto.UserMovieDTO;
import org.launchcode.moviedock.service.UserInSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ReviewController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserInSession userInSession;

    @GetMapping("review")
    public String displayReviewForm(@RequestParam Integer movieId,HttpServletRequest request, Model model){
            Optional<Movie> value = movieRepository.findById(movieId);
            Movie movie = value.get();
            User user = userInSession.getUserFromSession(request.getSession());
            Review review = reviewRepository.findByUserIdAndMovieId(movie.getId(),user.getId());

            UserMovieDTO userMovie =new UserMovieDTO();
            userMovie.setMovie(movie);
            userMovie.setUser(user);
            userMovie.setReview(review);

            if(review == null){
                model.addAttribute("dispText","Enter your review for " + movie.getName());
                model.addAttribute("userMovie", userMovie);
                model.addAttribute("buttonName","Submit Review");

                return "review/addReview.html";

            }
            else {
                model.addAttribute("userMovie", userMovie);
                model.addAttribute("dispText", "You have already reviewed the movie '" + movie.getName() + "'.  Update Review: ");
                model.addAttribute("oldReview", review);
                model.addAttribute("buttonName", "Update Review");
                return "review/addReview.html";
            }
    }

    @PostMapping("review")
    public String saveMovieReview(@ModelAttribute @Valid UserMovieDTO userMovie,
                                  Errors errors, HttpServletRequest request,
                                  Model model){


        if (!errors.hasErrors()) {
            Movie movie = userMovie.getMovie();
            User user = userInSession.getUserFromSession(request.getSession());
            //            Review review = userMovie.getReview();
            Review review = reviewRepository.findByUserIdAndMovieId(movie.getId(),user.getId());

            if(review != null){
                review.setMovie(movie);
                review.setUser(user);
                review.setReview_text(userMovie.getReview().getReview_text());
                review.setStar_rating(userMovie.getReview().getStar_rating());
                reviewRepository.save(review);
            }

            else{

                Review newReview = userMovie.getReview();
                newReview.setMovie(movie);
                newReview.setUser(user);
                reviewRepository.save(newReview);
            }


            model.addAttribute("user",user);
            return "user/profile.html";
        }

        return "redirect:";
    }


}
