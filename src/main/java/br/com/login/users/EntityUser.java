package br.com.login.users;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    @Setter(AccessLevel.NONE)
    private LocalDateTime dateCreated;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "permission", schema = SCHEMA,
            joinColumns = @JoinColumn(name = "user_id", columnDefinition = "bigint", referencedColumnName = COLUMN_ID,
                    foreignKey = @ForeignKey(name = "fk_user")))
    @Column(name = "permission_id")
    private List<Integer> permission;

    @Column(name = "last_password_change")
    private LocalDateTime lastPasswordChange;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "personal_data_id", referencedColumnName = PersonalData.COLUMN_ID,
        foreignKey = @ForeignKey(name = "fk_personal_data"), columnDefinition = "BIGINT")
    private PersonalData personalData;

    @PrePersist
    private void prePersist() {
        this.active = true;
        this.dateCreated = LocalDateTime.now();
    }

    protected LoginDTO loginDTO() {
        LoginDTO dto = new LoginDTO();
        dto.setUsername(this.email);
        return dto;
    }

    protected ProfileDTO profileDTO() {
        ProfileDTO dto = new ProfileDTO();
        dto.setEmail(this.email);
        dto.setCpf(this.personalData.getCpf());
        dto.setGender(this.personalData.getGender());
        dto.setDataCreated(this.dateCreated);
        dto.setLastPasswordChange(this.lastPasswordChange);
        return dto;
    }
}

