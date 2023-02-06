package com.example.springwithhibernate.model;

import com.example.springwithhibernate.entity.UserEntity;

public class User {
    Long id;
    String username;

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public static User toModel(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getUsername());
    }

}
