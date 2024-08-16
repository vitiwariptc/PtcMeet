package com.vitiwari.services;


import com.vitiwari.model.User;
import com.vitiwari.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        Optional<User> userFound = userRepository.findByUserName(user.getUserName());

        if(userFound.isPresent()) throw new UsernameAlreadyExistsException("Username already exists: " + user.getUserName());

        userFound = userRepository.findByEmailId(user.getEmailId());
        if(userFound.isPresent()) throw new EmailIdAlreadyExistsException("EmailId already exists: " + user.getEmailId());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUserName(username);

        String encodedPassword = passwordEncoder.encode(password);

        if (user.isPresent() && encodedPassword.equals(user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

}
