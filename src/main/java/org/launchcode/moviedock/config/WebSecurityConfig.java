package org.launchcode.moviedock.config;

import org.launchcode.moviedock.models.AuthenticationSuccessHandller;
import org.launchcode.moviedock.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // For the purposes of this project, CSRF protection is overkill
//                .csrf(AbstractHttpConfigurer::disable)
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/css/*").permitAll()
                            .requestMatchers("/images/*").permitAll()
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/signin").permitAll()
                            .requestMatchers("/signup").permitAll()
                            .requestMatchers("/profile/*").permitAll()
                            .requestMatchers("/signup-verify").permitAll()
                            .requestMatchers("/movies").permitAll()
                            .requestMatchers("/search").permitAll()
                            .anyRequest().authenticated();
                            //.anyRequest().permitAll();
                })
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/signin")
                            .successHandler(new AuthenticationSuccessHandller())
                            .permitAll();
                })
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}