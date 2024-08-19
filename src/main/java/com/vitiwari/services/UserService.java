package com.vitiwari.services;


import com.vitiwari.model.User;
import com.vitiwari.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        User userFound = userRepository.findByUserName(user.getUserName());
        if(userFound != null) throw new UsernameAlreadyExistsException("Username already exists: " + user.getUserName());

        userFound = userRepository.findByEmailId(user.getEmailId());
        if(userFound != null) throw new EmailIdAlreadyExistsException("EmailId already exists: " + user.getEmailId());

        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUserName(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return new User();
    }

}
