package org.launchcode.moviedock.security.config;

import org.launchcode.moviedock.security.AuthenticationSuccessHandller;
import org.launchcode.moviedock.security.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/css/*").permitAll()
                            .requestMatchers("/images/*").permitAll()
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/signin").permitAll()
                            .requestMatchers("/signup").permitAll()
                            .requestMatchers("/reset-password").permitAll()
                            .requestMatchers("/reset-password/verify").permitAll()
                            .requestMatchers("/profile/*").permitAll()
                            .requestMatchers("/signup-verify").permitAll()
                            .requestMatchers("/movies").permitAll()
                            .requestMatchers("/search").permitAll()
                            .requestMatchers("/user_search").permitAll()
                            .requestMatchers("/searched_profile").permitAll()
                            .requestMatchers("/search/*").permitAll()
                            .requestMatchers("/search/results").permitAll()
                            .requestMatchers("/search/movie-view/*").permitAll()
                            .requestMatchers("/movie-view/*").permitAll()
                            .anyRequest().authenticated();
//                            .anyRequest().permitAll();
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