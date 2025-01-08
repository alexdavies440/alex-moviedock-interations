package org.launchcode.moviedock.models.dto;

import jakarta.validation.constraints.NotNull;
import org.launchcode.moviedock.models.AppUser;
import org.launchcode.moviedock.models.Movie;

public class UserMovieDTO {

    @NotNull
    private AppUser user;

    @NotNull
    private Movie movie;

    public UserMovieDTO() {
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}