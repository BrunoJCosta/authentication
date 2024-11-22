package br.com.login.exception;

public class EmailAlreadyExists extends AuthException {

    public EmailAlreadyExists(String message) {
      super("Already exist this email: " + message);
    }
}
