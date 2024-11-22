package br.com.login.exception;

public class PasswordIsEmpty extends AuthException {

    public PasswordIsEmpty() {
        super("password can not empty");
    }
}
