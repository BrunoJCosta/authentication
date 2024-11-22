package br.com.login.users;

public record UserForm(String email, String name,
                       String cpf, String gender,
                       String password, String passwordConfirmation) {

}
