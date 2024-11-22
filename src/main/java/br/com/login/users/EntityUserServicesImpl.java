package br.com.login.users;

import br.com.login.configuration.Permission;
import br.com.login.configuration.UserDTO;
import br.com.login.configuration.token.TokenDTO;
import br.com.login.configuration.token.TokenService;
import br.com.login.exception.*;
import br.com.login.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class EntityUserServicesImpl implements EntityUserServices {

    public static final Exception NOT_FOUND = new Exception("user or password Invalid");
    private final EntityUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final PersonalDataRepository personalDataRepository;

    @Override
    public LoginDTO findByEmailUserDTO(String email, String password) throws Exception {
        EntityUser user = repository.findByEmailAndActiveTrue(email).orElseThrow(() -> NOT_FOUND);
        boolean matchesPassword = passwordEncoder.matches(password, user.getPassword());
        if (!matchesPassword)
            throw NOT_FOUND;
        LoginDTO userDTO = user.loginDTO();
        TokenDTO token = tokenService.generatedToken(email);
        userDTO.setToken(token.getToken());
        userDTO.setExpirationToken(token.getExpiration());
        userDTO.setExpirationRefresh(token.getExpirationRefresh());
        return userDTO;
    }

    @Override
    public ProfileDTO detail(UserDTO userDTO) throws UserNotFound {
        EntityUser user = repository.findByEmailAndActiveTrue(userDTO.getUsername())
                .orElseThrow(UserNotFound::new);
        return user.profileDTO();
    }

    @Override
    public ProfileDTO created(UserForm userForm, UserDTO principal) throws Exception {
        EntityUser user = new EntityUser();
        user.setUserCreated(principal.getUsername());

        if (StringUtils.empty(userForm.email()))
            throw new EmailIsEmpty();
        if (StringUtils.empty(userForm.cpf()))
            throw new CpfIsEmpty();
        if (StringUtils.empty(userForm.name()))
            throw new NameIsEmpty();

        if (StringUtils.empty(userForm.password()) || StringUtils.empty(userForm.passwordConfirmation()))
            throw new PasswordIsEmpty();
        if (Objects.equals(userForm.password(), userForm.passwordConfirmation()))
            throw new PasswordNotMatch();

        String encode = passwordEncoder.encode(userForm.password());
        user.setPassword(encode);
        user.addPermission(Permission.USER_COMUM);

        Optional<EntityUser> isEmailOpt = repository.findByEmailAndActiveTrue(userForm.email());
        if (isEmailOpt.isPresent())
            throw new EmailAlreadyExists(userForm.email());
        user.setEmail(userForm.email());

        personalDataRepository.findByCpfAndActiveTrue(userForm.cpf())
                .ifPresentOrElse(user::setPersonalData, () -> {
                    PersonalData personalData = new PersonalData();
                    personalData.setCpf(userForm.cpf());
                    personalData.setNome(userForm.name());
                    personalData.setGender(userForm.gender());
                    PersonalData save = personalDataRepository.save(personalData);
                    user.setPersonalData(save);
                });

        return repository.save(user).profileDTO();
    }

}
