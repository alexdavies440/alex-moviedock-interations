package org.launchcode.moviedock.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Entity;


@Entity
public class User extends AbstractEntity {

    @NotNull(message = "This field is required")
    private String username;

    @NotNull
    private String pwHash;

    @NotNull
    @Email
    private String email;

    public User () {}

    public User (String username, String password) {
        this.username = username;
        this.pwHash = password;
    }

    public String getUsername() {
        return username;
    }

}
