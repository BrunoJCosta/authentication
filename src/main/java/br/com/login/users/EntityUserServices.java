package br.com.login.users;

import br.com.login.configuration.UserDTO;
import br.com.login.exception.UserNotFound;

import java.util.Optional;

public interface EntityUserServices {

    Optional<ProfileDTO> findByEmailUserDTO(String email);

    ProfileDTO detail(UserDTO userDTO) throws UserNotFound;

    ProfileDTO created(UserForm userForm, UserDTO principal) throws Exception;

    void createdAutomatic(UserForm userForm) throws Exception;

}
