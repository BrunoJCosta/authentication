package br.com.login.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUtils {

    public final static String PREFIX_URL = "http://localhost:8081";

    public static Integer transformInteger(String str) {
        if (StringUtils.empty(str))
            return null;

        return Integer.getInteger(str.replaceAll("\\D*",""));
    }

    public static Long transformLong(String str) {
        if (StringUtils.empty(str))
            return null;

        return Long.getLong(str.replaceAll("\\D*",""));
    }
}
