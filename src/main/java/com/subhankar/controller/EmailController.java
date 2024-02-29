package com.subhankar.controller;

import com.subhankar.model.DTO.EmailRequestDTO;
import com.subhankar.model.DTO.Result;
import com.subhankar.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Result> sendEmail(@RequestBody EmailRequestDTO emailRequestDTO) {
           return emailService.sendEmail(emailRequestDTO.getTo());
    }
}
