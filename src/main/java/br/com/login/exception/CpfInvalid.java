package br.com.login.exception;

public class CpfInvalid extends BadRequestException {

    public CpfInvalid() {
        super("cpf");
    }
}
