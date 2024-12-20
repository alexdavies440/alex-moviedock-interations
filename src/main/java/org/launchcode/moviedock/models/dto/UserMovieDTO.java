package org.launchcode.moviedock.models.dto;

import jakarta.validation.constraints.NotNull;
import org.launchcode.moviedock.models.Movie;
import org.launchcode.moviedock.models.User;

public class UserMovieDTO {

    @NotNull
    private User user;

    @NotNull
    private Movie movie;

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
}
