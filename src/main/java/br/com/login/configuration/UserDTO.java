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
        // password: pass
        dto.password = "$2a$12$M4f0mNHtFcXTcDpW/SOsg.lVyMVZ4.SMMGiMWHV3h37HGsS3eXeB.";
        return dto;
    }
}
