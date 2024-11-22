package br.com.login.exception;

public class TokenNotFound extends AuthException {

    public TokenNotFound() {
        super("Token not found");
    }
}
