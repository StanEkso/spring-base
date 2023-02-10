package com.example.springwithhibernate.dto;

public class UserLoginDto {
    private String username;
    private String password;

    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserLoginDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
