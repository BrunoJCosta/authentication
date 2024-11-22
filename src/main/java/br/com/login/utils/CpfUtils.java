package br.com.login.utils;

public class CpfUtils {

    private static final int length_without_mask = 11;
    private static final int length_with_mask = 14;

    public static String removeMask(String cpf) {
        return StringUtils.empty(cpf) ? "" : cpf.replaceAll("[.-]","");
    }

    public static String putMask(String cpf) {
        if (StringUtils.empty(cpf))
            return "";

        if (cpf.length() == length_with_mask)
            return cpf;

        if (cpf.length() != length_without_mask)
            return "";

        String first = cpf.substring(0, 3);
        String second = cpf.substring(3, 6);
        String threeth = cpf.substring(6, 9);
        String fourth = cpf.substring(9, 11);

        return first + "." + second + "." + threeth + "-" + fourth;
    }

    public static void main(String[] args) {
        System.out.println(putMask("07659045350"));
    }

}