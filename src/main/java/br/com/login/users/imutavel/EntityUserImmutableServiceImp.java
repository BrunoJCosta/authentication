package br.com.login.users.imutavel;

import br.com.login.configuration.UserDTO;
import br.com.login.configuration.token.TokenDTO;
import br.com.login.configuration.token.TokenService;
import br.com.login.exception.AuthException;
import br.com.login.exception.UsernameOrPasswordNotFound;
import br.com.login.users.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class EntityUserImmutableServiceImp implements EntityUserImmutableService {

    public static final UsernameOrPasswordNotFound NOT_FOUND = new UsernameOrPasswordNotFound();
    private final EntityUserImmutableRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public LoginDTO findByEmailUserDTO(String email, String password) throws AuthException {
        EntityUserImmutable user = repository.findByEmailAndActiveTrue(email).orElseThrow(() -> NOT_FOUND);
        boolean matchesPassword = passwordEncoder.matches(password, user.getPassword());
        if (!matchesPassword)
            throw NOT_FOUND;
        TokenDTO token = tokenService.generatedToken(email);
        return user.loginDTO(token);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return repository.findByEmailAndActiveTrue(email).map(EntityUserImmutable::userDTO);
    }

}
