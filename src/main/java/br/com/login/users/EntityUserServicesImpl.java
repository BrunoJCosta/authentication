package br.com.login.users;

import br.com.login.configuration.token.TokenDTO;
import br.com.login.configuration.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EntityUserServicesImpl implements EntityUserServices {

    public static final Exception NOT_FOUND = new Exception("user or password Invalid");
    private final EntityUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public LoginDTO findByEmailUserDTO(String email, String password) throws Exception {
        EntityUser user = repository.findByEmail(email).orElseThrow(() -> NOT_FOUND);
        boolean matchesPassword = passwordEncoder.matches(password, user.getPassword());
        if (!matchesPassword)
            throw NOT_FOUND;
        LoginDTO userDTO = user.loginDTO();
        TokenDTO token = tokenService.generatedToken(email);
        userDTO.setToken(token.getToken());
        userDTO.setExpirationToken(token.getExpiration());
        userDTO.setExpirationToken(token.getExpirationRefresh());
        return userDTO;
    }

}
