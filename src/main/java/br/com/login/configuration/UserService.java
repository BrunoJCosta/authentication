package br.com.login.configuration;

import br.com.login.users.imutavel.EntityUserImmutableService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final EntityUserImmutableService service;

    @Override
    public UserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        return service.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }
}
