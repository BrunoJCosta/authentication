package br.com.login.exception;

public class CpfInvalid extends AuthException {

    public CpfInvalid() {
        super("cpf invalid");
    }
}
