package br.com.login.users;

import br.com.login.configuration.UserDTO;

public interface EntityUserServices {

    LoginDTO findByEmailUserDTO(String email, String senha) throws Exception;

    ProfileDTO detail(UserDTO userDTO);

}
