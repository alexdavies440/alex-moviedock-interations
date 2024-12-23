package org.launchcode.moviedock.service;

import org.launchcode.moviedock.data.UserRepository;
import org.launchcode.moviedock.models.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                // Look for user by username in userRepository
                .findByUsername(username)
                // If a match is found, map to new SecurityUser instance
                .map(SecurityUser::new)
                // Otherwise throw this exception
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

    }
}
