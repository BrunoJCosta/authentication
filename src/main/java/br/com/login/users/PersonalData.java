package br.com.login.users;

import br.com.login.utils.SchemaUtils;
import jakarta.persistence.*;

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
class PersonalData {

    protected static final String SCHEMA = SchemaUtils.USERS;
    protected static final String TABLE = "personal_data";
    public static final String COLUMN_ID = "pk";
    protected static final String GENERATOR = TABLE + "_gen";
    protected static final String SEQUENCE = TABLE + "_seq";

    @Id
    @GeneratedValue(generator = GENERATOR, strategy = GenerationType.SEQUENCE)
    @Column(name = COLUMN_ID)
    private Long id;

    @Column(name = "name")
    private String nome;

    @Column(name = "cpf", columnDefinition = "varchar(11)")
    private String cpf;

    @Column(name = "genero")
    private String genero;

    @Column(name = "ativo", columnDefinition = "boolean default true")
    private String ativo;

}
