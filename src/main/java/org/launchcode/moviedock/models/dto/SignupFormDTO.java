package org.launchcode.moviedock.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.launchcode.moviedock.data.ValidEmail;

public class SignupFormDTO extends SigninFormDTO {

    @NotBlank
    @ValidEmail // Custom email validator
    private String email;

    @NotBlank
    private String verifyPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

}
