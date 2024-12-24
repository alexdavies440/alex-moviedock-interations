package org.launchcode.moviedock.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SigninFormDTO {

    @NotBlank
//    @Size(min = 3, max = 45, message = "Username must be between 3 and 45 characters")
    private String username;

    @NotBlank
//    @Size(min = 5, max = 45, message = "Password must be between 5 and 45 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
