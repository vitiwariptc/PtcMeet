package com.vitiwari.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmailId;

    public void sendMail(String recipient, String body, String subject) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper =  new MimeMessageHelper(message, true);;

        helper.setFrom(fromEmailId);
        helper.setSubject(subject);
        helper.setTo(recipient);
        helper.setText(body, true);

        javaMailSender.send(message);
    }

    public boolean isEmailValid(String email) {
        String regex = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]+$";
        return email.matches(regex);
    }

}
