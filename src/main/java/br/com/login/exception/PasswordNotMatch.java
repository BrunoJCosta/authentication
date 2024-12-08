package br.com.login.exception;

public class PasswordNotMatch extends BadRequestException {

    public PasswordNotMatch() {
        super("password not match");
    }
}
