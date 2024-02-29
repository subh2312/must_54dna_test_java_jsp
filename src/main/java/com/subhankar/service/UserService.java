package com.subhankar.service;

import com.subhankar.model.DTO.LoginRequestDTO;
import com.subhankar.model.DTO.Result;
import com.subhankar.model.DTO.SignUpRequestDTO;
import com.subhankar.model.DTO.UpdateUserRequestDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    String getStatus();

    ResponseEntity<Result> addUser(SignUpRequestDTO signUpRequestDTO);

    ResponseEntity<Result> login(LoginRequestDTO loginRequestDTO);

    ResponseEntity<Result> getUser(String id);

    ResponseEntity<Result> updateUser(String id, UpdateUserRequestDTO updateUserRequestDTO);

    ResponseEntity<Result> deleteUser(String id);

    ResponseEntity<Result> getUserByUserId(String userId);
}
