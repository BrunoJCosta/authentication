package br.com.login.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
public enum Permission implements GrantedAuthority {

    USER_COMUM(1, "Usuário comum"),
    USER_ADMIN(2, "Admin");

    private final Integer code;
    private final String description;

    @Override
    public String getAuthority() {
        return this.name();
    }

    public static Permission getById(Integer id) throws Exception {
        return Arrays.stream(values()).filter(p -> Objects.equals(id, p.code)).findAny()
                .orElseThrow(() -> new Exception("permission not found"));
    }

    public static List<Permission> getAllById(List<Integer> ids) {
        return Arrays.stream(values()).filter(p -> ids.contains(p.getCode())).toList();
    }
}

