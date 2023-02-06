package com.example.springwithhibernate.controller;

import com.example.springwithhibernate.entity.UserEntity;
import com.example.springwithhibernate.exceptions.UserIsNotExistException;
import com.example.springwithhibernate.model.User;
import com.example.springwithhibernate.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LogManager.getLogger();
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<String> getUsers() {
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping
    public ResponseEntity postUser(@RequestBody UserEntity user) {
        logger.log(Level.INFO, "Post request: " + user);
        try {
            UserEntity userEntity = userService.registerUser(user);
            return ResponseEntity.ok(User.toModel(userEntity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        try {
            var user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserIsNotExistException e) {
            return ResponseEntity.badRequest().body("User is not exist");
        }
    }
}
