package br.com.login.exception;

public class TokenExpiration extends AuthException {

    public TokenExpiration() {
        super("token expiration");
    }
}
