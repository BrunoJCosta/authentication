package br.com.login.exception;

public class UserNotFound extends BadRequestException {

    public UserNotFound() {
        super("user not found");
    }
}
