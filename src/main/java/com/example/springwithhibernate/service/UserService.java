package com.example.springwithhibernate.service;

import com.example.springwithhibernate.entity.UserEntity;
import com.example.springwithhibernate.exceptions.UserAlreadyExistsException;
import com.example.springwithhibernate.exceptions.UserIsNotExistException;
import com.example.springwithhibernate.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileService fileService;

    public UserService(UserRepository userRepository, FileService fileService) {
        this.userRepository = userRepository;
        this.fileService = fileService;
    }
    public UserEntity registerUser(UserEntity user, MultipartFile avatar) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User already exists");
        }
        try {
            String fileName = fileService.saveFile(avatar, "avatars/" + user.getUsername());
            user.setAvatarUrl(fileName);
        } catch (Exception e) {
            e.printStackTrace();
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
