package com.vitiwari.services;

import com.vitiwari.model.User;
import com.vitiwari.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repo.findByUserName(username);

        if (user.isEmpty()) {
            System.out.println("NO USER FOUND");
            throw new UsernameNotFoundException("USER NOT FOUND");
        }

        User usr = user.get();
        return new MyUserDetails(usr);
    }
}
