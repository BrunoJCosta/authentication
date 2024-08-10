package br.com.login.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Permission implements GrantedAuthority {

    USER_COMUM(1, "Usuário Comum");

    private final Integer code;
    private final String description;

    @Override
    public String getAuthority() {
        return this.name();
    }
}

