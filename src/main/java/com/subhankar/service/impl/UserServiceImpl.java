package com.subhankar.service.impl;

import com.subhankar.exception.ResourceNotFoundException;
import com.subhankar.model.DTO.*;
import com.subhankar.service.UserService;
import com.subhankar.exception.UnauthorizedException;
import com.subhankar.model.DO.UserDO;
import com.subhankar.repository.UserRepository;
import com.subhankar.utils.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.subhankar.model.DTO.Result;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CustomValidator customValidator;
    public String getStatus() {
        return "User Service is up and running";
    }

    @Override
    public ResponseEntity<Result> addUser(SignUpRequestDTO signUpRequestDTO) {
        Map<String, String> error= customValidator.checkIsValidToCreate(signUpRequestDTO);

        if(!error.isEmpty()){
            return ResponseEntity.badRequest().body(Result.builder().status("error").message("Validation failed").data(error).build());
        }
        try{
            UserDO userDO = userRepository.save(UserDO.builder()
                    .email(signUpRequestDTO.getEmail())
                    .name(signUpRequestDTO.getName())
                    .nickName(signUpRequestDTO.getNickName())
                    .password(signUpRequestDTO.getPassword())
                    .phone(signUpRequestDTO.getPhone())
                    .userId(signUpRequestDTO.getUserId())
                    .build());

            SignUpResponseDTO signUpResponseDTO = SignUpResponseDTO.builder()
                    .id(userDO.getId())
                    .email(userDO.getEmail())
                    .name(userDO.getName())
                    .nickName(userDO.getNickName())
                    .phone(userDO.getPhone())
                    .userId(userDO.getUserId())
                    .build();
            return ResponseEntity.ok(Result.builder().status("success").message("User created successfully").data(signUpResponseDTO).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Result.builder().status("error").message("User creation failed").data(e.getMessage()).build());
        }
    }

    @Override
    public ResponseEntity<Result> login(LoginRequestDTO loginRequestDTO) {
        Map<String, String> error= customValidator.checkIsValidToLogin(loginRequestDTO);
        if (!error.isEmpty()) {
            return ResponseEntity.badRequest().body(Result.builder().status("error").message("Validation failed").data(error).build());
        }
        UserDO userDO = userRepository.findByEmailOrUserId(loginRequestDTO.getUserName());
        if (userDO == null) {
            throw new ResourceNotFoundException("User","email or userId", loginRequestDTO.getUserName());
        }
        if (!userDO.getPassword().equals(loginRequestDTO.getPassword())) {
            throw new UnauthorizedException();
        }
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .id(userDO.getId())
                .email(userDO.getEmail())
                .userId(userDO.getUserId())
                .token("token")
                .build();
        return ResponseEntity.ok(Result.builder().status("success").message("User logged in successfully").data(loginResponseDTO).build());
    }

    @Override
    public ResponseEntity<Result> getUser(String id) {
        Optional<UserDO> optionalUserDO = userRepository.findById(id);
        if (optionalUserDO.isEmpty()) {
            throw new ResourceNotFoundException("User","id", id);
        }
        UserDO userDO = optionalUserDO.get();
        return ResponseEntity.ok(Result.builder().status("success").message("User details fetched successfully").data(userDO).build());
    }

    @Override
    public ResponseEntity<Result> updateUser(String id, UpdateUserRequestDTO updateUserRequestDTO) {

        UserDO userDO = userRepository.findByEmailOrUserId(id);
        updatePropertyIfNotEmpty(updateUserRequestDTO.getEmail(), userDO::setEmail);
        updatePropertyIfNotEmpty(updateUserRequestDTO.getName(), userDO::setName);
        updatePropertyIfNotEmpty(updateUserRequestDTO.getNickName(), userDO::setNickName);
        updatePropertyIfNotEmpty(updateUserRequestDTO.getPhone(), userDO::setPhone);
        updatePropertyIfNotEmpty(updateUserRequestDTO.getPassword(), userDO::setPassword);
        return ResponseEntity.ok(Result.builder().status("success").message("User details updated successfully").data(userRepository.save(userDO)).build());
    }

    @Override
    public ResponseEntity<Result> deleteUser(String id) {
        UserDO userDO = userRepository.findByEmailOrUserId(id);
        userRepository.delete(userDO);
        return ResponseEntity.ok(Result.builder().status("success").message("User deleted successfully").data(null).build());
    }

    @Override
    public ResponseEntity<Result> getUserByUserId(String userId) {
        UserDO userDO = userRepository.findByEmailOrUserId(userId);
        return ResponseEntity.ok(Result.builder().status("success").message("User details fetched successfully").data(userDO).build());
    }

    private static void updatePropertyIfNotEmpty(String newValue, Consumer<String> setter) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        }
    }



}
