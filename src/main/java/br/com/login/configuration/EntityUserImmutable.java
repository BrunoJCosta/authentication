package br.com.login.configuration;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "permission", schema = SCHEMA,
            joinColumns = @JoinColumn(name = "user_id", columnDefinition = "bigint", referencedColumnName = COLUMN_ID,
                    foreignKey = @ForeignKey(name = "fk_user")))
    @Column(name = "permission_id")
    private List<Integer> permission;

    protected UserDTO userDTO() {
        UserDTO dto = new UserDTO();
        dto.setUsername(this.email);
        dto.setPassword(this.password);
        dto.setAuthorities(Permission.getAllById(permission));
        return dto;
    }
}
