package br.com.login.exception;

public class TokenInvalid extends BadRequestException {

    public TokenInvalid() {
        super("token invalid");
    }
}
