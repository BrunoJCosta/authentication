package br.com.login.users;

import br.com.login.configuration.UserDTO;

public interface EntityUserServices {

    UserDTO findByEmailUserDTO(String email, String senha) throws Exception;

}
