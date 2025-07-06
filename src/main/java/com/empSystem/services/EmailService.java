package com.empSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail_app_user}")
    private String from;

    public void sendEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Verify Your Account");
        message.setText("Hello, " + name + " please Click the link blow to verify your account\n " + "http://localhost:8080/verify/token");
        mailSender.send(message);
    }

    public void sendEmailToResetPassword(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Reset Password");
        message.setText("Hello, please Click the link blow to reset your password http://localhost:8080/auth/reset-password/" + token);
        mailSender.send(message);
    }
}
