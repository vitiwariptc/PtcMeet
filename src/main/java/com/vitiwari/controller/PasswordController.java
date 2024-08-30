package com.vitiwari.controller;

import com.vitiwari.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin
@RequestMapping("/password")
public class PasswordController {

    @Autowired
    UserService userService;

    @GetMapping("/forgotPassword")
    @ResponseBody
    public ResponseEntity<?> forgotPassword(@RequestParam("username") String username) {
        String msg = userService.forgotPassword(username);
        if(!msg.equals("Failed"))
            return ResponseEntity.ok("{\"msg\": \"Login Successfully\"}");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username Not Found");
    }

    @GetMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam("token") String token) {
        ModelAndView modelAndView = new ModelAndView("home.jsp");
        String username = userService.validateChangePasswordRequest(token);
        if(username.equals("Failed")) {
            modelAndView.addObject("msg", "Token Verification Failed");
            modelAndView.addObject("isHidden", "none");
        }else{
            modelAndView.addObject("username", username);
            modelAndView.addObject("msg", "Change the password");
            modelAndView.addObject("isHidden", "block");
        }
        return modelAndView;
    }

    @PostMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam("username") String username,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword) {

        ModelAndView modelAndView = new ModelAndView("home.jsp");
        if (newPassword.equals(confirmPassword)) {
            userService.changePassword(username, newPassword);
            modelAndView.addObject("msg", "Password changed successfully.");
            modelAndView.addObject("isHidden", "none");
        }
        else {
            modelAndView.addObject("msg", "Passwords do not match.");
            modelAndView.addObject("isHidden", "block");
            modelAndView.addObject("username", username);
        }
        return modelAndView;
    }
}
