package br.com.login.exception;

public class NameIsEmpty extends AuthException {

    public NameIsEmpty() {
        super("name can not empty");
    }
}
