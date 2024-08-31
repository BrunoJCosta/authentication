package br.com.login.configuration;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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

    protected UserDTO userDTO() {
        UserDTO dto = new UserDTO();
        dto.setUsername(email);
        dto.setPassword(password);
        dto.setAuthorities(List.of(Permission.USER_COMUM));
        return dto;
    }
}
