package com.subhankar.service;

import com.subhankar.model.DTO.Result;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    ResponseEntity<Result> sendEmail(String to);

}
