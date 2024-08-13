package com.vitiwari.services;

public class EmailIdAlreadyExistsException extends RuntimeException {
    public EmailIdAlreadyExistsException(String s) {
        super(s);
    }
}
