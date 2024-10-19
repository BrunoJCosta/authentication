package br.com.login.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDTO {

    private String email;
    private String name;
    private String cpf;
    private String gender;
    private LocalDateTime dataCreated;
    private LocalDateTime lastPasswordChange;

}
