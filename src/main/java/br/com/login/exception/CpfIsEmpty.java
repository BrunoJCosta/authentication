package br.com.login.exception;

public class CpfIsEmpty extends IsEmptyException {

    public CpfIsEmpty() {
        super("cpf can not empty");
    }
}
