package br.com.login.users;

public interface EntityUserServices {

    LoginDTO findByEmailUserDTO(String email, String senha) throws Exception;

}
