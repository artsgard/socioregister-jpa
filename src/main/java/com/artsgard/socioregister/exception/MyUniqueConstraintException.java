package com.artsgard.socioregister.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyUniqueConstraintException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MyUniqueConstraintException() {
    }

    public MyUniqueConstraintException(String message) {
        super(message);
    }

    public MyUniqueConstraintException(String message, Throwable cause) {
        super(message, cause);
    }
}
