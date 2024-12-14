package br.com.login.utils;

import java.util.Objects;

public class NumberUtils {

    public static boolean empty(Integer integer) {
        return Objects.isNull(integer) || integer <= 0;
    }

    public static boolean invalid(Integer integer) {
        return Objects.isNull(integer) || integer < 0;
    }

}
