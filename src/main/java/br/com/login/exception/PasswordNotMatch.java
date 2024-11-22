package br.com.login.exception;

public class PasswordNotMatch extends AuthException {

    public PasswordNotMatch() {
        super("password not match");
    }
}
