package br.com.login.exception;

public class TokenNotFound extends BadRequestException {

    public TokenNotFound() {
        super("Token not found");
    }
}
