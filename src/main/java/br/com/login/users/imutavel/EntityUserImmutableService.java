package br.com.login.users.imutavel;

import br.com.login.configuration.UserDTO;
import br.com.login.users.LoginDTO;

import java.util.Optional;

public interface EntityUserImmutableService {

    LoginDTO findByEmailUserDTO(String email, String password) throws Exception;

    Optional<UserDTO> findByEmail(String email);
}
