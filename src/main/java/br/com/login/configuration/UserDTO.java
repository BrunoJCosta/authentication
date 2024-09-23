package br.com.login.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Setter
public class UserDTO implements UserDetails {

    private String password;
    private String username;
    private List<Permission> authorities;

}
