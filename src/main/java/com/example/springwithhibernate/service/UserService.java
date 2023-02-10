package com.example.springwithhibernate.service;

import com.example.springwithhibernate.dto.UserLoginDto;
import com.example.springwithhibernate.entity.UserEntity;
import com.example.springwithhibernate.exceptions.UserAlreadyExists;
import com.example.springwithhibernate.exceptions.UserIsNotExistException;
import com.example.springwithhibernate.model.User;
import com.example.springwithhibernate.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserEntity registerUser(UserEntity user) throws UserAlreadyExists {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExists("User already exists");
        }
        return userRepository.save(user);
    }

    public UserEntity getUserById(Long id) throws UserIsNotExistException {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return user;
        }
        throw new UserIsNotExistException("User is not exist");
    }

    public UserEntity getUserByUsername(String username) throws UserIsNotExistException {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UserIsNotExistException("User is not exist");
    }

    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
