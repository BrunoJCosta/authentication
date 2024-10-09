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
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String username;

    @Column(name = "expiration")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private LocalDateTime expiration;

    @Column(name = "date_created")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private LocalDateTime dateCreated;

    public boolean isValid() {
        return LocalDateTime.now().isBefore(this.expiration);
    }

    public boolean isNotValid() {
        return !this.isValid();
    }

    public EntityToken(String token, String username, LocalDateTime expiration) {
        this.token = token;
        this.username = username;
        this.expiration = expiration;
    }

    @PrePersist
    private void prePersist(){
        this.dateCreated = LocalDateTime.now();
    }

    protected TokenDTO dto() {
        TokenDTO dto = new TokenDTO();
        dto.setToken(this.token);
        dto.setExpiration(this.expiration);
        return dto;
    }
}


