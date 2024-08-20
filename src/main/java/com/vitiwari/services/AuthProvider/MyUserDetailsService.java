package com.vitiwari.services.AuthProvider;

import com.vitiwari.model.User;
import com.vitiwari.repository.UserRepository;
import com.vitiwari.services.AuthProvider.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUserName(username);

        if (user == null) {
            System.out.println("NO USER FOUND");
            throw new UsernameNotFoundException("USER NOT FOUND");
        }

        return new MyUserDetails(user);
    }
}
