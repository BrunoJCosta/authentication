package br.com.login.configuration.token;

import br.com.login.users.LoginDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static br.com.login.configuration.token.TokenStatic.*;

@Entity
@Table(name = TABLE, schema = "security")
@SequenceGenerator(
        name = GENERATOR,
        sequenceName = SEQUENCE,
        allocationSize = 1,
        schema = "security"
)
@NoArgsConstructor
class EntityToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR)
    @Column(name = COLUMN_ID, columnDefinition = "bigserial")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "token", columnDefinition = "varchar(255)")
    @Getter
    private String token;

    @Column(name = "username")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.NONE)
    private String username;

    @Column(name = "token_expiration")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private LocalDateTime tokenExpiration;

    @Column(name = "token_refresh_expiration")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.PUBLIC)
    private LocalDateTime tokenRefreshExpiration;

    @Column(name = "date_created")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private LocalDateTime dateCreated;

    public boolean isRefreshValid() {
        return this.tokenRefreshExpiration != null && LocalDateTime.now().isBefore(this.tokenRefreshExpiration);
    }

    public boolean isRefreshNotValid() {
        return !this.isRefreshValid();
    }

    public boolean isTokenValid() {
        return this.tokenExpiration != null && LocalDateTime.now().isBefore(this.tokenExpiration);
    }

    public boolean isTokenNotValid() {
        return !this.isTokenValid();
    }

    public EntityToken(String token, String username, LocalDateTime expiration) {
        this.token = token;
        this.username = username;
        this.tokenRefreshExpiration = expiration;
        this.tokenExpiration = LocalDateTime.now().plusHours(8);
    }

    @PrePersist
    private void prePersist() {
        this.dateCreated = LocalDateTime.now();
    }

    protected TokenDTO dto() {
        TokenDTO dto = new TokenDTO();
        dto.setToken(this.token);
        dto.setExpiration(this.tokenRefreshExpiration);
        return dto;
    }

    protected LoginDTO loginDTO() {
        LoginDTO dto = new LoginDTO();
        dto.setExpiration(this.tokenRefreshExpiration);
        dto.setToken(this.token);
        return dto;
    }

    protected LoginDTO loginDTO(String username) {
        LoginDTO dto = this.loginDTO();
        dto.setUsername(username);
        return dto;
    }

}


