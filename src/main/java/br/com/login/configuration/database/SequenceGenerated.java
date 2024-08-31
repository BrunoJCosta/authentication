package br.com.login.configuration.database;

import org.hibernate.MappingException;
import org.hibernate.dialect.sequence.PostgreSQLSequenceSupport;
import org.hibernate.dialect.sequence.SequenceSupport;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SequenceGenerated implements SequenceSupport {

    @Override
    public String getSelectSequenceNextValString(String sequenceName) {
        return new PostgreSQLSequenceSupport().getSelectSequenceNextValString(sequenceName);
    }

    @Override
    public String getCreateSequenceString(String sequenceName) throws MappingException {
        return "create sequence if not exists " + sequenceName;
    }

}
