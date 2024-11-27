package br.com.login.exception;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class InvalidException extends Exception {

    private final String message;

    public InvalidException(String message) {
        this.message = message + " is invalid";
    }

    public InvalidException(String message, boolean plural) {
        String sufixo = plural ? " is" : " are";
        this.message = message + sufixo + " invalid";
    }
}
