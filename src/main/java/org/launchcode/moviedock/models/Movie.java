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

    @ManyToMany
    @JoinTable(name = "favoriteList")
    private Set<User> favoriteUsers = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "toWatchList")
    private Set<User> toWatchUsers = new HashSet<>();

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

    public Set<User> getFavUsers() {
        return favoriteUsers;
    }

    public Set<User> getToWatchUsers() {
        return toWatchUsers;
    }

    public void setFavoriteUsers(Set<User> favoriteUsers){
        this.favoriteUsers = favoriteUsers;
    }

    public void setToWatchUsers(Set<User> toWatchUsers){
        this.toWatchUsers = toWatchUsers;
    }

    public void addFavoriteUser(User user){
        this.favoriteUsers.add(user);
    }

    public void addToWatchUser(User user) {
        this.toWatchUsers.add(user);
    }


    @Override
    public String toString() {
        return name;
    }
}
