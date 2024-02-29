package com.subhankar.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.subhankar.model.DTO.Result;
import com.subhankar.service.OtpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpServiceImpl implements OtpService {
    @Value("${otp.key}")
    private String key;
    private static final Integer EXPIRE_MIN=3;
    private final LoadingCache<String, Integer> otpCache;

    public OtpServiceImpl()
    {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    @Override
    public Integer generateOTP()
    {
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        otpCache.put(key, OTP);
        return OTP;
    }

    @Override
    public Integer getOPTByKey()
    {
        return otpCache.getIfPresent(key);
    }

    @Override
    public ResponseEntity<Result> validateOTP(Integer otp) {
        Integer otpFromCache = otpCache.getIfPresent(key);
        boolean flag= otpFromCache != null && otpFromCache.equals(otp);
        if(flag){
            clearOTPFromCache();
            return ResponseEntity.ok(Result.builder().status("success").message("OTP validated successfully").build());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.builder().status("error").message("OTP is invalid or expired").build());

    }

    @Override
    public void clearOTPFromCache() {
        otpCache.invalidate(key);
    }

}
