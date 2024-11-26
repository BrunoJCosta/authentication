package br.com.login.configuration;

import br.com.login.users.EntityUserServices;
import br.com.login.users.ProfileDTO;
import br.com.login.users.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoutineUserAdmin {

    private final EntityUserServices entityUserService;

    @Bean
    public void createdUser() throws Exception {
        String email = "bruno@gmail.com";
        Optional<ProfileDTO> userAdmin = entityUserService.findByEmailUserDTO(email);
        if (userAdmin.isPresent())
            return;

        String nome = "Bruno jereissati da Costa";
        String cpf = "34164480068";
        String gender = "Masculine";
        String senha = "bruno@123";
        UserForm form = new UserForm(email, nome, cpf, gender, senha, senha);
        entityUserService.createdAutomatic(form);

    }
}
