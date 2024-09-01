package br.com.login.users;

import br.com.login.configuration.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EntityUserServicesImpl implements EntityUserServices {

    public static final Exception NOT_FOUND = new Exception("user or password Invalid");
    private final EntityUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findByEmailUserDTO(String email, String password) throws Exception {
        EntityUser user = repository.findByEmail(email).orElseThrow(() -> NOT_FOUND);
        boolean matchesPassword = passwordEncoder.matches(password, user.getPassword());
        if (!matchesPassword)
            throw NOT_FOUND;
        return user.userDTO();
    }

}
