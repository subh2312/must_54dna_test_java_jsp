package com.subhankar.service;

import com.subhankar.model.DTO.Result;
import org.springframework.http.ResponseEntity;

public interface OtpService {

    Integer generateOTP();

    Integer getOPTByKey();

    ResponseEntity<Result> validateOTP(Integer otp);

    void clearOTPFromCache();
}
