package br.com.login.exception;

public class UsernameOrPasswordNotFound extends BadRequestException {

    public UsernameOrPasswordNotFound() {
        super("user or password Invalid");
    }
}
