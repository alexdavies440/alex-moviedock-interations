package org.launchcode.moviedock.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.*;//for ENTITY ,ManyToMany etc.
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User extends AbstractEntity {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotEmpty
    private String pwHash;



    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Review> reviewsList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_favourite_movies",
            joinColumns = @JoinColumn(name = "favorite_user_id",referencedColumnName = "id") ,
            inverseJoinColumns = @JoinColumn(name="favorite_movie_id",referencedColumnName = "id")
    )
    private Set<Movie> favoriteMovies = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_to_watch_movies",
            joinColumns = @JoinColumn(name = "to_watch_user_id",referencedColumnName = "id") ,
            inverseJoinColumns = @JoinColumn(name="to_watch_movie_id",referencedColumnName = "id")
    )
    private Set<Movie> toWatchMovies = new HashSet<>();


    public User () {}

    public User (String username, String email, String password) {
        super();
        this.username = username;
        this.email = email;
        this.pwHash = encoder.encode(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<Review> getReviewsList() {
        return reviewsList;
    }

    public Set<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }

    public Set<Movie> getToWatchMovies() {
        return toWatchMovies;
    }

    public void setFavoriteMovies(Set<Movie> favoriteMovies){
        this.favoriteMovies = favoriteMovies;
    }

    public void setToWatchMovies(Set<Movie> toWatchMovies){
        this.toWatchMovies = toWatchMovies;
    }

    public void addFavoriteMovies(Movie movie) {
        this.favoriteMovies.add(movie);
    }

    public void addToWatchMovies(Movie movie) {
        this.toWatchMovies.add(movie);
    }



    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}
