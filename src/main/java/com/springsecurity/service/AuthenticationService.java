package com.springsecurity.service;

import com.springsecurity.model.User;
import com.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByName(login);
        if(!validateUser(user)) {
            throw new UsernameNotFoundException("User cannot authenticate");
        }
        return user;
    }

    private boolean validateUser(User user) {
        boolean valid = false;
        if(user != null) {
            valid = true;
        }
        return valid;
    }
}
