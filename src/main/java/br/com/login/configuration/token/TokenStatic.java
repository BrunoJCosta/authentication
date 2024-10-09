package br.com.login.configuration.token;

public class TokenStatic {

    protected final static String TABLE = "token";
    public final static String COLUMN_ID = "pk";
    protected final static String SEQUENCE = TABLE + "_" + COLUMN_ID + "_seq";
    protected final static String GENERATOR = TABLE + "_" + COLUMN_ID + "_gen";
}
