package org.launchcode.moviedock.config;

import org.launchcode.moviedock.models.AuthenticationSuccessHandller;
import org.launchcode.moviedock.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Properties;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private AppUserDetailsService appUserDetailsService;


//@Bean
//    JavaMailSender mailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("devdazzler840@gmail.com");
//        mailSender.setPassword("Jurr@$$1cP@rk");
//        return mailSender;
//    }

//    @Bean // this is a template message that we can pre-load with default state
//    SimpleMailMessage templateMessage() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("customerservice@mycompany.example");
//        message.setSubject("Your order");
//        return message;
//    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // For the purposes of this project, CSRF protection is overkill
                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/signup").permitAll()
                            .requestMatchers("/signin").permitAll()
                            .requestMatchers("/css/**").permitAll()
                            .requestMatchers("/profile").permitAll()
                            .requestMatchers("/search").permitAll()

                            .requestMatchers("/sendMail").permitAll()

                            .anyRequest().authenticated();
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