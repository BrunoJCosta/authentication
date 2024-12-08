package br.com.login.exception;

public class EmailIsEmpty extends BadRequestException {

    public EmailIsEmpty() {
        super("Email can not empty!");
    }
}
