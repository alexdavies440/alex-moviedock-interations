package org.launchcode.moviedock.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.launchcode.moviedock.service.AppUserDetailsService;


@Entity
public class AppUser extends AbstractEntity {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private String role;

    @NotNull
    private boolean enabled;

    @NotNull
    private String verificationCode;

    public AppUser() {}

    public AppUser(String username, String email, String password, String role, boolean enabled, String verificationCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.verificationCode = verificationCode;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean isEnabled) { this.enabled = isEnabled; }

    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }

    public String getVerificationCode() { return verificationCode; }

}