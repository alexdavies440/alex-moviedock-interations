package org.launchcode.moviedock.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.*;//for ENTITY ,ManyToMany etc.
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

//    @NotEmpty
//    private String pwHash;
//
//    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @NotBlank
    private String password;


    private String roles;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Review> reviewsList = new ArrayList<>();

    @ManyToMany(mappedBy = "favUser")
    private final List<Movie> favoriteMovies = new ArrayList<>();

    @ManyToMany(mappedBy = "toWatchUser")
    private final List<Movie> toWatchMovies = new ArrayList<>();


    public User () {}

    public User (String username, String email, String password, String roles) {
        this.username = username;
        this.email = email;
//        this.pwHash = encoder.encode(password);
        this.password = password;
        this.roles = roles;
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

//    public String getPwHash() {
//        return pwHash;
//    }


    public String getPassword() {
        return password;
    }

    public List<Review> getReviewsList() {
        return reviewsList;
    }

    public List<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }

    public List<Movie> getToWatchMovies() {
        return toWatchMovies;
    }


//    public boolean isMatchingPassword(String password) {
//        return encoder.matches(password, pwHash);
//    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
}