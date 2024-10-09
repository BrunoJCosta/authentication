package br.com.login.configuration.token;

import br.com.login.configuration.UserDTO;
import br.com.login.controller.TokenForm;
import org.springframework.security.core.Authentication;

public interface TokenService {

    TokenDTO generatedToken(String username);
    String recoveryEmailByToken(String token);

    void verifyToken(TokenForm form, UserDTO authentication);
}
