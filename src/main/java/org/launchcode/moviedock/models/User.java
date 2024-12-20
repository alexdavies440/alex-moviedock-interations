package org.launchcode.moviedock.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.Entity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



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

    public User () {}

    public User (String username, String email, String password) {
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

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}
