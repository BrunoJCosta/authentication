package br.com.login.exception;

public class EmailAlreadyExists extends AlreadyExistsException {

    public EmailAlreadyExists(String message) {
      super("Already exist this email: " + message);
    }
}
