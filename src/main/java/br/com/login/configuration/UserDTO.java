package br.com.login.configuration;

import br.com.login.users.Permission;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Setter
public class UserDTO implements UserDetails {

    private String username;
    private String token;
    private List<Permission> authorities;

    public String getPassword() {
        return "";
    }
}
