package br.com.login.users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter(AccessLevel.PACKAGE)
public class UserListDTO {

    private Long id;
    private String email;
    private String name;
    private String cpf;
    private String gender;
    private Set<Integer> permission;
    private LocalDateTime dateCreated;
}
