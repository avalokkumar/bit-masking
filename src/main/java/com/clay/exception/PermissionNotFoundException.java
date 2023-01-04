package com.clay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PermissionNotFoundException extends Exception{

    public PermissionNotFoundException() {
        super();
    }

    public PermissionNotFoundException(String message) {
        super(message);
    }
}
