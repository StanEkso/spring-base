package com.example.springwithhibernate.controller;

import com.example.springwithhibernate.dto.UserLoginDto;
import com.example.springwithhibernate.entity.UserEntity;
import com.example.springwithhibernate.exceptions.UserAlreadyExistsException;
import com.example.springwithhibernate.exceptions.UserIsNotExistException;
import com.example.springwithhibernate.model.User;
import com.example.springwithhibernate.service.AuthService;
import com.example.springwithhibernate.service.DefaultMailService;
import com.example.springwithhibernate.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final DefaultMailService mailService;
    private final FileService fileService;

    public AuthController(AuthService authService, DefaultMailService mailService, FileService fileService) {
        this.authService = authService;
        this.mailService = mailService;
        this.fileService = fileService;
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
    public ResponseEntity<String> registerUser(@RequestPart("img") MultipartFile file, @RequestPart UserEntity dto) {
        UserEntity user = null;
        try {
            user = authService.registerUser(dto, file);
            long startTime = System.currentTimeMillis();
            this.mailService.sendRegisterEmail(user);
            long endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
            return ResponseEntity.ok("User is registered");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("User is already registered");
        }
    }

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("img") MultipartFile file) {
        String filename = fileService.saveFile(file, "avatars");
        return ResponseEntity.ok(filename);
    }
}
