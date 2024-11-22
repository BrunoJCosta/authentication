package br.com.login.exception;

public class EmailIsEmpty extends AuthException {

    public EmailIsEmpty() {
        super("Email can not empty!");
    }
}
