//package org.launchcode.moviedock.service;
//
//import org.launchcode.moviedock.data.ProfileRepository;
//import org.launchcode.moviedock.models.Profile;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserService implements UserDetailsService {
//
//    @Autowired
//    ProfileRepository profileRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Optional<Profile> profile = profileRepository.findByUsername(username);
//
//        if(profile.isPresent()) {
//            Profile profileObj = profile.get();
//            return User.builder()
//                    .username(profileObj.getUsername())
//                    .password(profileObj.getPassword())
//                    .build();
//        } else {
//            throw new UsernameNotFoundException(username);
//        }
//    }
//}
