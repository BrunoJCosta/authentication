package br.com.login.exception;

public class CpfIsEmpty extends AuthException {

    public CpfIsEmpty() {
        super("cpf can not empty");
    }
}
