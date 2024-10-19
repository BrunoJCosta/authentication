package br.com.login.configuration.token;

import br.com.login.users.LoginDTO;

public interface TokenService {

    TokenDTO generatedToken(String username);

    String recoveryEmailByToken(String token);

    LoginDTO verifyToken(TokenForm form);
}
