package org.launchcode.moviedock.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.launchcode.moviedock.models.Movie;
import org.launchcode.moviedock.models.Review;
import org.launchcode.moviedock.models.User;

public class UserMovieDTO {

    @NotNull
    private User user;

    @NotNull
    private Movie movie;

//    @Valid
    @NotNull
    private Review review;

    public UserMovieDTO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
