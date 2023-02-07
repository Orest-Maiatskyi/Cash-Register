package com.epam.cashregister.services.crypto;

import java.security.SecureRandom;
import java.util.Base64;

public class Util {

    public static byte[] generate64BitSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }

    public static String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static String encodeBase64WithoutPadding(byte[] data) {
        return Base64.getEncoder().withoutPadding().encodeToString(data);
    }

    public static byte[] decodeBase64(String data) {
        return Base64.getDecoder().decode(data);
    }

}
