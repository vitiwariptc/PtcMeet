package com.vitiwari.services;


import com.vitiwari.model.RefreshToken;
import com.vitiwari.model.User;
import com.vitiwari.repository.RefreshTokenRepository;
import com.vitiwari.repository.UserRepository;
import com.vitiwari.response.JWTResponse;
import com.vitiwari.services.AuthProvider.MyUserDetails;
import com.vitiwari.services.Exceptions.EmailIdAlreadyExistsException;
import com.vitiwari.services.Exceptions.UsernameAlreadyExistsException;
import com.vitiwari.services.JWT.JWTService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;


    public User registerUser(User user) throws MessagingException {

        if(!emailService.isEmailValid(user.getEmailId())) throw new RuntimeException("Invalid EmailId");

        User userFound = userRepository.findByUserName(user.getUserName());

        if(userFound != null) throw new UsernameAlreadyExistsException("Username already exists: " + user.getUserName());

        userFound = userRepository.findByEmailId(user.getEmailId());
        if(userFound != null) throw new EmailIdAlreadyExistsException("EmailId already exists: " + user.getEmailId());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String token = jwtService.generateToken(user);

        String body = """
                <div style="max-width: 600px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);">
                        <h2 style="color: #333;">Email Verification</h2>
                        <p style="color: #555;">
                            Please click the link below to verify your email address:
                        </p>
                        <p>
                            <a href="http://localhost:8080/api/users/verify?token=%s"
                               style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px;">
                                Verify Email Address
                            </a>
                        </p>
                        <p style="color: #999; font-size: 12px;">
                            If you did not request this verification, please ignore this email.
                        </p>
                    </div>
                """.formatted(token);

        emailService.sendMail(user.getEmailId(), body, "Email Verification");

        return userRepository.save(user);
    }


    public JWTResponse loginUser(String username, String password) throws AuthenticationException {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        MyUserDetails mud = (MyUserDetails) auth.getPrincipal();

        User user = mud.getUser();

        if(auth.isAuthenticated()){
            return JWTResponse.builder()
                    .token(jwtService.generateToken(user))
                    .refreshToken(refreshTokenService.generateOrUpdateRefreshToken(user).getRefreshToken())
                    .msg("Login Successfully")
                    .build();
        }
        return null;
    }


    public String verify(String token) {

        if(jwtService.validateToken(token)) {
            String username = jwtService.extractUserName(token);
            User user = userRepository.findByUserName(username);
            user.setVerified(true);
            userRepository.save(user);
            return "Verification Completed Successfully";
        }

        return "Failed";
    }


    public String forgotPassword(String username) {

        User usr = userRepository.findByUserName(username);

        if(usr == null) return "Failed";

        String token = jwtService.generateToken(usr);

        String body = """
                <div style="max-width: 600px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);">
                        <h2 style="color: #333;">Password Change</h2>
                        <p style="color: #555;">
                            Please click the link below to change your password:
                        </p>
                        <p>
                            <a href="http://localhost:8080/password/changePassword?token=%s"
                               style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px;">
                                Change Password
                            </a>
                        </p>
                        <p style="color: #999; font-size: 12px;">
                            If you did not request for this password Change, please ignore this email.
                        </p>
                    </div>
                """.formatted(token);

        try {
            emailService.sendMail(usr.getEmailId(), body, "Change Password");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "Please Follow the link sent on your email to change your password";
    }


    public String validateChangePasswordRequest(String token) {
        if(jwtService.validateToken(token)) return jwtService.extractUserName(token);
        return "Failed";
    }

    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByUserName(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public JWTResponse verifyRefreshToken(String refreshToken) {
        RefreshToken rt = refreshTokenService.verifyRefreshToken(refreshToken);

        if(rt != null){
            User user = userRepository.findByRefreshToken(rt);
            if(user != null) {
                return JWTResponse.builder()
                        .token(jwtService.generateToken(user))
                        .refreshToken(refreshTokenService.generateOrUpdateRefreshToken(user).getRefreshToken())
                        .msg("Successfully generated new JWTToken using Refresh Token")
                        .build();
            }
        }
        return null;
    }
}
