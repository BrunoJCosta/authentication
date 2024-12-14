package br.com.login.utils;

public class SqlUtils {

    public static String puttingQuote(String str) {
        if (StringUtils.empty(str))
            return "''";
        return "'".concat(str).concat("'");
    }

    public static String puttingPercentage(String str) {
        if (StringUtils.empty(str))
            return "%%";
        return "%".concat(str.replaceAll(" ", "%")).concat("%");
    }

}
