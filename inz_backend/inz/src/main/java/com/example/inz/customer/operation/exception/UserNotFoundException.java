package com.example.inz.customer.operation.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException{

    private final HttpStatus code;

    public UserNotFoundException(String message, HttpStatus code){
        super(message);
        this.code = code;
    }

    public HttpStatus getCode() {
        return code;
    }
}
