package com.subhankar.controller;

import com.subhankar.model.DTO.Result;
import com.subhankar.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @GetMapping("/generate")
    public Integer generateOTP() {
        return otpService.generateOTP();
    }

    @GetMapping("/get")
    public Integer getOPTByKey() {
        return otpService.getOPTByKey();
    }

    @GetMapping("/clear")
    public void clearOTPFromCache() {
        otpService.clearOTPFromCache();
    }

    @GetMapping("/validate")
    public ResponseEntity<Result> validateOTP(@RequestParam String otp) {
        return otpService.validateOTP(Integer.parseInt(otp));
    }
}
