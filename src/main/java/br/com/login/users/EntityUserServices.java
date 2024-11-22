package br.com.login.users;

import br.com.login.configuration.UserDTO;
import br.com.login.exception.UserNotFound;

public interface EntityUserServices {

    LoginDTO findByEmailUserDTO(String email, String senha) throws Exception;

    ProfileDTO detail(UserDTO userDTO) throws UserNotFound;

    ProfileDTO created(UserForm userForm, UserDTO principal) throws Exception;

}
