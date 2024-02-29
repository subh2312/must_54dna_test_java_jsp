package com.subhankar.service.impl;

import com.subhankar.model.DTO.EmailResponseDTO;
import com.subhankar.model.DTO.Result;
import com.subhankar.service.EmailService;
import com.subhankar.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("email.username")
    private String fromEmail;
    @Value("${email.subject}")
    private String subject;

    private final JavaMailSender javaMailSender;
    private final OtpService otpService;
    @Override
    public ResponseEntity<Result> sendEmail(String to) {

        String body = "Please enter the OTP to verify your email: \n\n"+otpService.generateOTP()+"\n\n" +
                "\n Please Note: " +
                "\n 1. This is an auto-generated email. Please do not reply to this email." +
                "\n 2. OTP is valid for 3 minutes.";

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
            LocalDateTime sentAt = LocalDateTime.now();
            return ResponseEntity.ok(Result.builder().status("success").message("Email sent successfully").data(
                    EmailResponseDTO.builder().to(to).subject(subject).body(body).sentAt(sentAt).build()
            ).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Result.builder().status("error").message("Email sending failed").data(e.getMessage()).build());
        }
    }
}
