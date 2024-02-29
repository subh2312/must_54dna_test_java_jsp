package com.subhankar.utils;

import com.subhankar.model.DTO.LoginRequestDTO;
import com.subhankar.model.DTO.SignUpRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.subhankar.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class CustomValidator {
    private final UserRepository userRepository;

    public Map<String,String> checkIsValidToLogin(LoginRequestDTO loginRequestDTO){
        Map<String, String> validationErrors = new HashMap<>();

        if (loginRequestDTO.getUserName() == null || loginRequestDTO.getUserName().isEmpty()) {
            validationErrors.put("email", "Email is required");
        }
        if (loginRequestDTO.getPassword() == null || loginRequestDTO.getPassword().isEmpty()) {
            validationErrors.put("password", "Password is required");
        }

        if(loginRequestDTO.getPassword() != null && !isValidPassword(loginRequestDTO.getPassword())){
            validationErrors.put("password", "Invalid password format");
        }

        return validationErrors;
    }

    public Map<String, String> checkIsValidToCreate(SignUpRequestDTO signUpRequestDTO) {
        Map<String, String> validationErrors = new HashMap<>();

        // Check for empty fields
        if (signUpRequestDTO.getEmail() == null || signUpRequestDTO.getEmail().isEmpty()) {
            validationErrors.put("email", "Email is required");
        }
        if (signUpRequestDTO.getPassword() == null || signUpRequestDTO.getPassword().isEmpty()) {
            validationErrors.put("password", "Password is required");
        }
        if (signUpRequestDTO.getPhone() == null || signUpRequestDTO.getPhone().isEmpty()) {
            validationErrors.put("phone", "Phone number is required");
        }
        if (signUpRequestDTO.getNickName() == null || signUpRequestDTO.getNickName().isEmpty()) {
            validationErrors.put("nickName", "Nickname is required");
        }
        if (signUpRequestDTO.getUserId() == null || signUpRequestDTO.getUserId().isEmpty()) {
            validationErrors.put("userId", "User ID is required");
        }
        // Validate email format using regex
        if (signUpRequestDTO.getEmail() != null && !isValidEmail(signUpRequestDTO.getEmail())) {
            validationErrors.put("email", "Invalid email format");
        }

        // Validate phone number format using regex
        if (signUpRequestDTO.getPhone() != null && !isValidPhoneNumber(signUpRequestDTO.getPhone())) {
            validationErrors.put("phone", "Invalid phone number format");
        }

        // Validate name format using regex
        if (signUpRequestDTO.getName() != null && !isValidName(signUpRequestDTO.getName())) {
            validationErrors.put("name", "Invalid name format");
        }

        if(signUpRequestDTO.getName() != null && signUpRequestDTO.getName().length() < 3) {
            validationErrors.put("name", "Name should be at least 3 characters long");
        }

        if(signUpRequestDTO.getName() != null && signUpRequestDTO.getName().length() > 50) {
            validationErrors.put("name", "Name should be at most 50 characters long");
        }

        if (signUpRequestDTO.getUserId()!= null && userRepository.existsByUserId(signUpRequestDTO.getUserId())) {
            validationErrors.put("userId", "User ID already exists");
        }

        if (signUpRequestDTO.getEmail() != null && userRepository.existsByEmail(signUpRequestDTO.getEmail())) {
            validationErrors.put("email", "Email already exists");
        }

        if (signUpRequestDTO.getPhone() != null && userRepository.existsByPhone(signUpRequestDTO.getPhone())) {
            validationErrors.put("phone", "Phone number already exists");
        }

        return validationErrors;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Regex for password format (example: allows alphabets, digits, and special characters)
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,20}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^(\\+[0-9]{1,3}[- ]?)?\\(?[0-9]{3}\\)?[-. ]?[0-9]{3}[-. ]?[0-9]{4}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phone).matches();
    }

    private boolean isValidName(String name) {
        // Regex for name format (example: allows alphabets, space, and hyphen)
        String nameRegex = "^[a-zA-Z\\s-]+$";
        Pattern pattern = Pattern.compile(nameRegex);
        return pattern.matcher(name).matches();
    }
}

