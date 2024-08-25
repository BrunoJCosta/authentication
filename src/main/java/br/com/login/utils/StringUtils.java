package br.com.login.utils;

import java.util.Objects;

@SuppressWarnings("unused")
public class StringUtils {

    public static boolean empty(String str) {
        return Objects.isNull(str) || str.isEmpty() || str.isBlank();
    }

    public static boolean filled(String str) {
        return !empty(str);
    }

}
