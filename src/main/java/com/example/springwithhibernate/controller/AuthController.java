package com.example.springwithhibernate.controller;

import com.example.springwithhibernate.dto.UserLoginDto;
import com.example.springwithhibernate.entity.UserEntity;
import com.example.springwithhibernate.exceptions.UserAlreadyExists;
import com.example.springwithhibernate.exceptions.UserIsNotExistException;
import com.example.springwithhibernate.model.User;
import com.example.springwithhibernate.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto dto) {
        User user = null;
        try {
            user = authService.loginUser(dto);
        } catch (UserIsNotExistException e) {
            return ResponseEntity.badRequest().body("User is not exist!");
        }
        if (user != null) {
            return ResponseEntity.ok("User is logged in");
        }
        return ResponseEntity.badRequest().body("Provided password is incorrect");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity dto) {
        UserEntity user = null;
        try {
            user = authService.registerUser(dto);
            return ResponseEntity.ok("User is registered");
        } catch (UserAlreadyExists e) {
            return ResponseEntity.badRequest().body("User is already registered");
        }
    }
}
