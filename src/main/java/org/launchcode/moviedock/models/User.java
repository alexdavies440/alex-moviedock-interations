package org.launchcode.moviedock.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Entity;


@Entity
public class User {

    int id;

    @NotNull(message = "This field is required")
    @Size(min=3, max=45, message = "Username must be between 3 and 45 characters")
    String username;

    @Email
    String email;
}
