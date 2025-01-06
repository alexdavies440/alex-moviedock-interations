package org.launchcode.moviedock.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    @ManyToMany(mappedBy = "favoriteMovies")
    private Set<AppUser> favoriteUsers = new HashSet<>();


    @ManyToMany(mappedBy = "toWatchMovies")
    private Set<AppUser> toWatchUsers = new HashSet<>();


    public Movie() {
    }

    public Movie(String name, int search_count) {
        super();
        this.name = name;
        this.search_count = search_count;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<AppUser> getFavUsers() {
        return favoriteUsers;
    }

    public Set<AppUser> getToWatchUsers() {
        return toWatchUsers;
    }

    public void setFavoriteUsers(Set<AppUser> favoriteUsers){
        this.favoriteUsers = favoriteUsers;
    }

    public void setToWatchUsers(Set<AppUser> toWatchUsers){
        this.toWatchUsers = toWatchUsers;
    }

    public void addFavoriteUser(AppUser user){
        this.favoriteUsers.add(user);
    }

    public void addToWatchUser(AppUser user) {
        this.toWatchUsers.add(user);
    }


    @Override
    public String toString() {
        return name;
    }
}
