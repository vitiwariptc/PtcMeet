package com.vitiwari.services;


import com.vitiwari.model.User;
import com.vitiwari.repository.UserRepository;
import com.vitiwari.services.Exceptions.EmailIdAlreadyExistsException;
import com.vitiwari.services.Exceptions.UsernameAlreadyExistsException;
import com.vitiwari.services.JWT.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User registerUser(User user) {
        User userFound = userRepository.findByUserName(user.getUserName());

        if(userFound != null) throw new UsernameAlreadyExistsException("Username already exists: " + user.getUserName());

        userFound = userRepository.findByEmailId(user.getEmailId());
        if(userFound != null) throw new EmailIdAlreadyExistsException("EmailId already exists: " + user.getEmailId());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUserName(username);

        String encodedPassword = passwordEncoder.encode(password);

        if (user != null && encodedPassword.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    public String verify(String username, String password) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        if (auth.isAuthenticated()) return jwtService.generateToken(username, password);
        return "Failed";
    }
}
