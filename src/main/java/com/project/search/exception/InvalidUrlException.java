package com.project.search.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidUrlException extends Exception {

    public InvalidUrlException(String message) {

        super(message);
    }
}