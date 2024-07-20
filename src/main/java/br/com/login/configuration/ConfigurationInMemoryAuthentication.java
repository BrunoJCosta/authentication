package br.com.login.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

@Component
public class ConfigurationInMemoryAuthentication implements AuthenticationManager {

    private final String user = "user1";
    private final String senha = "user1Pass";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object principal = authentication.getPrincipal();
        Object credentials = authentication.getCredentials();
        boolean mesmoUser = Objects.equals(principal, user);
        boolean mesmaSenha = Objects.equals(credentials, senha);
        if (mesmoUser && mesmaSenha)
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getCredentials(), Collections.emptyList());

        throw new RuntimeException("senha ou usuário invalido");
    }
}