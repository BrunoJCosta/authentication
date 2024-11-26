package br.com.login.exception;

public class UsernameOrPasswordNotFound extends AuthException {

    public UsernameOrPasswordNotFound() {
        super("user or password Invalid");
    }
}
