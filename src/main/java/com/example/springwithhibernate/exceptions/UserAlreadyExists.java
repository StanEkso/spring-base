package com.example.springwithhibernate.exceptions;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists(String message) {
        super(message);
    }
}
