package org.launchcode.moviedock.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
public class Review extends AbstractEntity{

    @ManyToOne
    @NotNull(message= "select movie to review")
    private Movie movie;


    @ManyToOne
    @NotNull
    private User user;

    @NotBlank
    @Size(min = 3,max = 500)
    private String review_text;

    //    provide a dropdown to select options from 1-5
    @NotNull
    private int star_rating;

    public Review(Movie movie, User user, String review_text, int star_rating) {
        this.movie = movie;
        this.user = user;
        this.review_text = review_text;
        this.star_rating = star_rating;
    }

    public Review() {
    }

    public Movie getMovie() {
        return movie;
    }

    public User getUser() {
        return user;
    }

    public String getReview_text() {
        return review_text;
    }

    public int getStar_rating() {
        return star_rating;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public void setStar_rating(int star_rating) {
        this.star_rating = star_rating;
    }
}
