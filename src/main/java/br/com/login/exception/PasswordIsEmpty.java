package br.com.login.exception;

public class PasswordIsEmpty extends BadRequestException {

    public PasswordIsEmpty() {
        super("password can not empty");
    }
}
