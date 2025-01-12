package org.launchcode.moviedock.models;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class ThemeChangeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Theme theme = new Theme("light");

        if (!theme.getMode().equals("dark") && !theme.getMode().equals("light")) {
            Theme defaultTheme = new Theme("dark");
            theme = defaultTheme;
        }

        request.setAttribute("theme", theme);

        return true;
    }

}