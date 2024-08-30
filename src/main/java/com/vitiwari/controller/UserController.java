package com.vitiwari.controller;

import com.vitiwari.model.User;
import com.vitiwari.response.JWTResponse;
import com.vitiwari.services.Exceptions.EmailIdAlreadyExistsException;
import com.vitiwari.services.UserService;
import com.vitiwari.services.Exceptions.UsernameAlreadyExistsException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String Greet(HttpServletRequest req) {
        return "Welcome To PTCMeet " + req.getSession().getId();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok("{\"msg\": \"Registration Successfull, Verify your Email\"}");
        } catch (UsernameAlreadyExistsException | EmailIdAlreadyExistsException | MessagingException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        JWTResponse response;

        try {
            response = userService.loginUser(user.getUserName(), user.getPassword());
        }catch(AuthenticationException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(401).body("Invalid Username and Password");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {
        String msg = userService.verify(token);
        if (!msg.equals("Failed")) {
            return ResponseEntity.ok(msg);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Failed");
        }
    }


    @GetMapping("/verifyRefreshToken")
    public ResponseEntity<?> verifyRefreshToken(@RequestParam String refreshToken) {
        JWTResponse res = userService.verifyRefreshToken(refreshToken);
        if(res == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification Failed");
        return ResponseEntity.ok(res);
    }


}


// csrf token is used while updating data on server not generally on get request