package br.com.login.exception;

public class TokenExpiration extends BadRequestException {

    public TokenExpiration() {
        super("token expiration");
    }
}
