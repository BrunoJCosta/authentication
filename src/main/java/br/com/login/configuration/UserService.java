package br.com.login.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = UserDTO.montarDTO();
        if (Objects.equals(userDTO.getUsername(), username))
            return userDTO;

        throw new UsernameNotFoundException("username not found");
    }
}
