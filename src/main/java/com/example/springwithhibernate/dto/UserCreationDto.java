package com.example.springwithhibernate.dto;

import com.example.springwithhibernate.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

public class UserCreationDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private MultipartFile file;

    public UserCreationDto(Long id, String email, String username, String password, MultipartFile file) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "UserCreationDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", file=" + file +
                '}';
    }

    public UserEntity toUserEntity() {
        return new UserEntity(id, email, username, password);
    }
}
