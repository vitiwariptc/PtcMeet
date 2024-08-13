package com.vitiwari.services;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String s) {
        super(s);
    }
}
