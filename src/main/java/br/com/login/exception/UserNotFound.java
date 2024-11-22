package br.com.login.exception;

public class UserNotFound extends AuthException {

    public UserNotFound() {
        super("user not found");
    }
}
