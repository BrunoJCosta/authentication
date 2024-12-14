package br.com.login.users;

import br.com.login.configuration.UserDTO;
import br.com.login.exception.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EntityUserServices {

    Optional<ProfileDTO> findByEmailUserDTO(String email);

    ProfileDTO detail(UserDTO userDTO) throws UserNotFound;

    ProfileDTO created(UserForm userForm, UserDTO principal) throws Exception;

    void createdAutomatic(UserForm userForm) throws PasswordNotMatch, PasswordIsEmpty,
            CpfInvalid, NameIsEmpty, EmailIsEmpty, CpfIsEmpty;

    List<UserListDTO> list(Pageable pageable, String name, String permission, String automatic,
                                              String cpf, String garden, String email);
}
