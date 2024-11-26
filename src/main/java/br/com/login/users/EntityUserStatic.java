package br.com.login.users;

import br.com.login.utils.SchemaUtils;

public class EntityUserStatic {

    public static final String SCHEMA = SchemaUtils.USERS;
    public static final String TABLE = "users";
    public static final String COLUMN_ID = "pk";
    public static final String GENERATOR = TABLE + "_gen";
    public static final String SEQUENCE = TABLE + "_" + COLUMN_ID;

    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ACTIVE = "active";
}
