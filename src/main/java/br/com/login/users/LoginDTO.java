package br.com.login.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoginDTO {

    private String token;
    private String username;
    private LocalDateTime expiration;
}
