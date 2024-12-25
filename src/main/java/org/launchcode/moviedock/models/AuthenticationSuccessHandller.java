package org.launchcode.moviedock.models;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

// Upon successful authentication, determines what page user is redirected to
public class AuthenticationSuccessHandller extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        // Set custom page to display when user logs in depending on authorities
        if(isAdmin) {
            setDefaultTargetUrl("/settings");
        } else {
            setDefaultTargetUrl("/profile");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
