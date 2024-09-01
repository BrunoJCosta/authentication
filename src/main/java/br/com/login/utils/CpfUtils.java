package br.com.login.utils;

public class CpfUtils {

    public static String removeCpfMask(String cpf) {
        return StringUtils.empty(cpf) ? "" : cpf.replaceAll("[.-]","");
    }

}