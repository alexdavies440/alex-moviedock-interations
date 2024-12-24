package org.launchcode.moviedock.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(registry -> {
                    registry
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/signup").permitAll()
                            .requestMatchers("/signin").permitAll()
                            .requestMatchers("/css/**").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(formLogin -> formLogin.permitAll())
                .build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails appUser = User.builder()
                .username("test")
                .password(passwordEncoder().encode("test"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(appUser);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
