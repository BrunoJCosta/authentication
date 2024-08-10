package br.com.login.configuration;

import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
public class UserDTO implements UserDetails {

    private String password;
    private String username;
    private List<Permission> authorities;

    public static UserDTO montarDTO() {
        UserDTO dto = new UserDTO();
        dto.authorities = List.of(Permission.USER_COMUM);
        dto.username = "user";
        dto.password = "pass";
        return dto;
    }
}
