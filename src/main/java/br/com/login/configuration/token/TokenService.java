package br.com.login.configuration.token;

import br.com.login.configuration.UserDTO;

public interface TokenService {

    TokenDTO generatedToken(String username);
    String recoveryEmailByToken(String token);

}
