package br.com.login.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProfileDTO {

    private String email;
    private String name;
    private String cpf;
    private String gender;
    private List<Integer> permission;
    private LocalDateTime dataCreated;
    private LocalDateTime lastPasswordChange;

}
