package br.com.login.users;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static br.com.login.users.EntityUserStatic.*;

@Entity
@Table(name = TABLE, schema = SCHEMA)
@SequenceGenerator(
        name = GENERATOR,
        sequenceName = SEQUENCE,
        allocationSize = 1,
        schema = SCHEMA
)
@Getter
@Setter
class EntityUser {

    @Id
    @GeneratedValue(generator = GENERATOR, strategy = GenerationType.SEQUENCE)
    @Column(name = COLUMN_ID)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = COLUMN_EMAIL)
    private String email;

    @Column(name = COLUMN_PASSWORD)
    private String password;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "ativo", columnDefinition = "boolean default true")
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "personal_data_id", referencedColumnName = PersonalData.COLUMN_ID,
        foreignKey = @ForeignKey(name = "fk_personal_data"), columnDefinition = "BIGINT")
    private PersonalData personalData;

    @PrePersist
    private void prePersist() {
        this.ativo = true;
        this.dateCreated = LocalDateTime.now();
    }

    protected LoginDTO loginDTO() {
        LoginDTO dto = new LoginDTO();
        dto.setUsername(this.email);
        return dto;
    }
}

