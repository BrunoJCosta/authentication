package br.com.login.exception;

public class TokenInvalid extends AuthException {

    public TokenInvalid() {
        super("token invalid");
    }
}
