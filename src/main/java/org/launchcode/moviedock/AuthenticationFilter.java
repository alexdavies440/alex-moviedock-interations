package org.launchcode.moviedock;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.launchcode.moviedock.controllers.AuthenticationController;
import org.launchcode.moviedock.models.User;
import org.launchcode.moviedock.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    private static final List<String> whitelist =
            Arrays.asList("/","/signin", "/signup", "/css/styles.css", "/css/styles-light.css", "/test", "/profile", "/search");

    private static boolean isWhitelisted(String path) {
        for (String pathRoot : whitelist) {
            if (path.equals(pathRoot)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {

        if (isWhitelisted(request.getRequestURI())) {
            return true;
        }

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if (user != null) {
            return true;
        }

        response.sendRedirect("/signin");
        return false;
    }

}
