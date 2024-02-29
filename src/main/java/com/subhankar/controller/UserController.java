package com.subhankar.controller;

import com.subhankar.model.DTO.Result;
import com.subhankar.model.DTO.SignUpRequestDTO;
import com.subhankar.model.DTO.UpdateUserRequestDTO;
import com.subhankar.service.UserService;
import com.subhankar.model.DTO.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @GetMapping("/status")
    public String getStatus() {
        return userService.getStatus();
    }

    @PostMapping("/signup")
    public ResponseEntity<Result> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        return userService.addUser(signUpRequestDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return userService.login(loginRequestDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<Result> getUserByUserId(@PathVariable String userId) {
        return userService.getUserByUserId(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateUser(@PathVariable String id, @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        return userService.updateUser(id, updateUserRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

}
