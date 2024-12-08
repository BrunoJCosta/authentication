package br.com.login.exception;

public class CpfIsEmpty extends BadRequestException {

    public CpfIsEmpty() {
        super("cpf can not empty");
    }
}
