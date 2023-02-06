package com.example.springwithhibernate.exceptions;

public class UserIsNotExistException extends Exception{
    public UserIsNotExistException(String message) {
        super(message);
    }
}
