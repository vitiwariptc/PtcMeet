package com.vitiwari.services.Exceptions;

public class EmailIdAlreadyExistsException extends RuntimeException {
    public EmailIdAlreadyExistsException(String s) {
        super(s);
    }
}
