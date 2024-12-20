package org.launchcode.moviedock.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Movie extends AbstractEntity{

    @NotNull(message = "Movie name is needed")
    private String name;

    private int search_count;

    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Review> reviewsList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "favorite")
    private final List<User> favUser = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "toWatchList")
    private final List<User> toWatchUser = new ArrayList<>();

    public Movie(String name, int search_count) {
        this.name = name;
        this.search_count = search_count;
    }

    public Movie() {
    }

    public String getName() {
        return name;
    }

    public int getSearch_count() {
        return search_count;
    }

    public void setSearch_count(int search_count) {
        this.search_count = search_count;
    }

    public List<Review> getReviewsList() {
        return reviewsList;
    }

    public List<User> getFavUser() {
        return favUser;
    }

    public List<User> getToWatchUserUser() {
        return toWatchUser;
    }

    @Override
    public String toString() {
        return name;
    }
}
