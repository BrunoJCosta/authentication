package br.com.login.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final EntityUserImmutableRepository userImmutableRepository;

    @Override
    public UserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        EntityUserImmutable user = userImmutableRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        return user.userDTO();
    }
}
