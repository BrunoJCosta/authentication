package br.com.login.exception;

import lombok.Getter;

@Getter
public class IsEmptyException extends Exception {

    private final String message;

    public IsEmptyException(String message) {
        this.message =message + " can not empty";
    }

}
