package br.com.login.configuration.token;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TokenDTO {

    private String token;
    private LocalDateTime expiration;
    private LocalDateTime expirationRefresh;

}
