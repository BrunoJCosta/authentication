package br.com.login.exception;

public class NameIsEmpty extends BadRequestException {

    public NameIsEmpty() {
        super("name can not empty");
    }
}
