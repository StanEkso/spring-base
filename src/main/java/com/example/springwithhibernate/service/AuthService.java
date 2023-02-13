package com.example.springwithhibernate.service;

import com.example.springwithhibernate.dto.UserLoginDto;
import com.example.springwithhibernate.entity.UserEntity;
import com.example.springwithhibernate.exceptions.UserAlreadyExistsException;
import com.example.springwithhibernate.exceptions.UserIsNotExistException;
import com.example.springwithhibernate.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AuthService {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
    private final UserService userService;
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    private UserEntity hashUserPassword(UserEntity user) {
        String hashedPassword = encoder.encode(user.getPassword());
        return new UserEntity(user.getId(), user.getEmail(), user.getUsername(), hashedPassword);
    }

    private boolean isPasswordsEquals(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }

    public User loginUser(UserLoginDto dto) throws UserIsNotExistException {
            UserEntity user = userService.getUserByUsername(dto.getUsername());
            if (user != null && isPasswordsEquals(dto.getPassword(), user.getPassword())) {
                return User.toModel(user);
            }
            return null;
    }

    public UserEntity registerUser(UserEntity user, MultipartFile file) throws UserAlreadyExistsException {
        return userService.registerUser(hashUserPassword(user), file);
    }
}
