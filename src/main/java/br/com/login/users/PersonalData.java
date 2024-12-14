package br.com.login.users;

import br.com.login.utils.SchemaUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static br.com.login.users.PersonalData.*;

@Entity
@Table(name = TABLE, schema = SCHEMA,
        uniqueConstraints = @UniqueConstraint(name = "uk_cpf", columnNames = "cpf"))
@SequenceGenerator(
        name = GENERATOR,
        sequenceName = SEQUENCE,
        allocationSize = 1,
        schema = SCHEMA
)
@Getter
@Setter
class PersonalData {

    protected static final String SCHEMA = SchemaUtils.USERS;
    protected static final String TABLE = "personal_data";
    public static final String COLUMN_ID = "pk";
    protected static final String GENERATOR = TABLE + "_gen";
    protected static final String SEQUENCE = TABLE + "_seq";

    @Id
    @GeneratedValue(generator = GENERATOR, strategy = GenerationType.SEQUENCE)
    @Column(name = COLUMN_ID)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cpf", columnDefinition = "varchar(11)", unique = true, nullable = false)
    private String cpf;

    @Column(name = "gender", columnDefinition = "varchar(70)")
    private String gender;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

    @PrePersist
    private void prePersist() {
        this.active = true;
    }
}
