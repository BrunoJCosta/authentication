package br.com.login.users.imutavel;

import br.com.login.configuration.Permission;
import br.com.login.configuration.UserDTO;
import br.com.login.configuration.token.TokenDTO;
import br.com.login.users.LoginDTO;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;


import java.util.List;

import static br.com.login.users.EntityUserStatic.*;

@Entity
@Table(name = TABLE, schema = SCHEMA)
@Immutable
@Getter
class EntityUserImmutable {

    @Id
    @Column(name = COLUMN_ID)
    private Long id;

    @Column(name = COLUMN_EMAIL)
    private String email;

    @Column(name = COLUMN_PASSWORD)
    private String password;

    @Column(name = COLUMN_ACTIVE, columnDefinition = "boolean default true")
    private boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "permission", schema = SCHEMA,
            joinColumns = @JoinColumn(name = "user_id", columnDefinition = "bigint", referencedColumnName = COLUMN_ID,
                    foreignKey = @ForeignKey(name = "fk_user")))
    @Column(name = "permission_id")
    private List<Integer> permission;

    protected LoginDTO loginDTO(TokenDTO token) {
        LoginDTO dto = new LoginDTO();
        dto.setUsername(this.email);
        dto.setToken(token.getToken());
        dto.setExpirationToken(token.getExpiration());
        dto.setExpirationRefresh(token.getExpirationRefresh());
        return dto;
    }

    protected UserDTO userDTO() {
        UserDTO dto = new UserDTO();
        dto.setUsername(this.email);
        dto.setPassword(this.password);
        dto.setAuthorities(Permission.getAllById(permission));
        return dto;
    }
}
