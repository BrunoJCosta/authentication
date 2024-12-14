package br.com.login.utils;

import java.util.List;

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

    public static String censurado(String cpf) {
        if (StringUtils.empty(cpf))
            return "";
        if (cpf.length() != length_with_mask && cpf.length() != length_without_mask)
            return "";
        var prefixo = cpf.substring(0,3);
        if (cpf.length() == length_without_mask) {
            var sufixo = cpf.substring(9);
            return prefixo + ".***.***-" + sufixo;
        }
        var sufixo = cpf.substring(12);
        return prefixo + ".***.***-" + sufixo;
    }

    public static void main(String[] args) {
        String cpfMask = censurado("0765904550");
        String cpfWithoutMask = censurado("076.590453-50");
        System.out.println(cpfMask);
        System.out.println(cpfWithoutMask);
    }

    public static boolean valid(String cpf) {
        return !invalid(cpf);
    }

    public static boolean invalid(String cpf) {
        if (StringUtils.empty(cpf))
            return true;

        String without = removeMask(cpf);

        if (without.length() != length_without_mask)
            return true;

        List<String> cpfInvalidos = List.of(
                "00000000000", "11111111111", "22222222222", "33333333333", "44444444444",
                "55555555555", "66666666666", "77777777777", "88888888888", "99999999999");
        if (cpfInvalidos.contains(without))
            return true;

        Integer[] digity = new Integer[length_without_mask];
        for (int i = 0; i < without.length(); i++) {
            String str = String.valueOf(without.charAt(i));
            digity[i] = Integer.parseInt(str);
        }

        int soma1 = 0;
        for (int multiplicador = 10, index = 0; multiplicador >= 2; multiplicador--, index++) {
            soma1 += digity[index] * multiplicador;
        }
        int resto1 = (soma1 * 10) % length_without_mask;
        if (resto1 == 10) resto1 = 0;

        int soma2 = 0;
        for (int multiplicador = length_without_mask, index = 0; multiplicador >= 2; multiplicador--, index++) {
            soma2 += digity[index] * multiplicador;
        }
        int resto2 = (soma2 * 10) % length_without_mask;
        if (resto2 == 10) resto2 = 0;

        //index digitos 9 – 10
        return resto1 != digity[9] || resto2 != digity[10];
    }

}