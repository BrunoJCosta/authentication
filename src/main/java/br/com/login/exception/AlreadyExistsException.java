package br.com.login.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends Exception {

    private final String message;

    public AlreadyExistsException(String message) {
        this.message = message + " already exist";
    }
}
