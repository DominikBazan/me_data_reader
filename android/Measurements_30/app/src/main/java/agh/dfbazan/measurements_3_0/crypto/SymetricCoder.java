package agh.dfbazan.measurements_3_0.crypto;

import java.util.Random;
import java.util.StringTokenizer;

public class SymetricCoder {

    private int key;
    private final String separators = "ABCDEFVXYZ";


    public SymetricCoder() {
        key = 123;
    }

    public int getKey() {
        return key;
    }

    //Pobranie losowego separatora
    private char getRandomSeparator(Random randomForSep) {
        int randPos = randomForSep.nextInt(separators.length());
        return separators.charAt(randPos);
    }

    //Zaszyfrowanie znaku
    private String encryptChar(char ch) {
        int ich = (int) ch;
        return Integer.toString(ich + key);
    }

    //Zaszyfrowanie tekstu
    public String encrypt(String value) {
        Random randomForSep = new Random();

        char sep1 = getRandomSeparator(randomForSep);
        String sep1S = Character.toString(sep1);
        String encrypted = new String(sep1S);

        //Wprowadzenie losowych separatorów do tekstu
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            String codeCh = encryptChar(ch);
            char sep2 = getRandomSeparator(randomForSep);
            encrypted = encrypted + codeCh + sep2;
        }
        return encrypted;
    }

    public char decryptChar(String sIntCode) {
        int intCode = Integer.parseInt(sIntCode);
        return (char) (intCode - key);
    }


    //Odszyfrowanie wiadomosci
    public String decrypt(String encrypted) {
        String decrypted = new String();

        StringTokenizer st = new StringTokenizer(encrypted, separators);

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            char ch = decryptChar(token);
            decrypted = decrypted + ch;
        }

        return decrypted;
    }

    public static void main(String[] args) {
        String text = "Sekretny komunikat ąęćśżźóĄĘĆŻŹŚÓ";

        System.out.println("text=" + text);

        SymetricCoder coder = new SymetricCoder();

        String etext = coder.encrypt(text);

        System.out.println("zaszyfrowany=" + etext + "|");

        String dtext = coder.decrypt(etext);

        System.out.println("odszyfrowany=" + dtext + "|");

    }

}