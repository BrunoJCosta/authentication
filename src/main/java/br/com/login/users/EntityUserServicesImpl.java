package br.com.login.users;

import br.com.login.configuration.UserDTO;
import br.com.login.exception.*;
import br.com.login.utils.CpfUtils;
import br.com.login.utils.RequestUtils;
import br.com.login.utils.SqlUtils;
import br.com.login.utils.StringUtils;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class EntityUserServicesImpl implements EntityUserServices {


    private final EntityUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final PersonalDataRepository personalDataRepository;
    private final EntityManager entityManager;

    @Override
    public Optional<ProfileDTO> findByEmailUserDTO(String email) {
        return repository.findByEmailAndActiveTrue(email).map(EntityUser::profileDTO);
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

        validation(userForm);

        String encode = passwordEncoder.encode(userForm.password());
        user.setPassword(encode);
        user.addPermission(Permission.USER_COMUM);

        Optional<EntityUser> isEmailOpt = repository.findByEmailAndActiveTrue(userForm.email());
        if (isEmailOpt.isPresent())
            throw new EmailAlreadyExists(userForm.email());

        return savePersonalData(userForm, user);
    }

    private static void validation(UserForm userForm) throws EmailIsEmpty, CpfIsEmpty, CpfInvalid, NameIsEmpty, PasswordIsEmpty, PasswordNotMatch {
        if (StringUtils.empty(userForm.email()))
            throw new EmailIsEmpty();
        if (StringUtils.empty(userForm.cpf()))
            throw new CpfIsEmpty();
        if (CpfUtils.invalid(userForm.cpf()))
            throw new CpfInvalid();
        if (StringUtils.empty(userForm.name()))
            throw new NameIsEmpty();

        if (StringUtils.empty(userForm.password()) || StringUtils.empty(userForm.passwordConfirmation()))
            throw new PasswordIsEmpty();
        if (!Objects.equals(userForm.password(), userForm.passwordConfirmation()))
            throw new PasswordNotMatch();
    }

    @Override
    public void createdAutomatic(UserForm userForm) throws PasswordNotMatch, PasswordIsEmpty, CpfInvalid, NameIsEmpty, EmailIsEmpty, CpfIsEmpty {
        validation(userForm);
        EntityUser user = new EntityUser();

        String encode = passwordEncoder.encode(userForm.password());
        user.setPassword(encode);
        user.addPermission(Permission.USER_ADMIN);

        user.setAutomatic(true);

        Optional<EntityUser> isEmailOpt = repository.findByEmailAndActiveTrue(userForm.email());
        if (isEmailOpt.isPresent()) {
            isEmailOpt.get().profileDTO();
            return;
        }

        savePersonalData(userForm, user);
    }

    @Override
    public List<UserListDTO> list(Pageable pageable, String name, String permissionStr, String automatic,
                                  String cpf, String gender, String email) {
        String nameFind = SqlUtils.puttingPercentage(name);
        String cpfFind = SqlUtils.puttingPercentage(cpf);
        String genderFind = SqlUtils.puttingPercentage(gender);
        String emailTreaty = StringUtils.empty(email) ? "" : email.toLowerCase().strip();
        String emailFind = SqlUtils.puttingPercentage(emailTreaty);
        Integer permission = RequestUtils.transformInteger(permissionStr);

        return repository.listAll(nameFind, cpfFind, genderFind, emailFind, permission, pageable)
                .stream().map(EntityUser::listDTO).toList();
    }

    private ProfileDTO savePersonalData(UserForm userForm, EntityUser user) {
        user.setEmail(userForm.email().strip().toLowerCase());

        personalDataRepository.findByCpfAndActiveTrue(userForm.cpf())
                .ifPresentOrElse(user::setPersonalData, () -> {
                    PersonalData personalData = new PersonalData();
                    personalData.setCpf(userForm.cpf());
                    personalData.setName(userForm.name());
                    personalData.setGender(userForm.gender());
                    PersonalData save = personalDataRepository.save(personalData);
                    user.setPersonalData(save);
                });

        return repository.save(user).profileDTO();
    }
}
