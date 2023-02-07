package com.epam.cashregister.services.crypto;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PBKDF2 {

    public static byte[] generateDefaultHash(String data, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(data.toCharArray(), salt, 10000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    public static byte[] generateCustomHash(String data, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(data.toCharArray(), salt, iterationCount, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = Util.generate64BitSalt();
        //byte[] hash = generateDefaultHash("12345", salt);
        byte[] hash = generateDefaultHash("12345", Util.decodeBase64("NELOWuNClZk="));
        System.out.println("Salt: " + Util.encodeBase64(salt));
        System.out.println("Hash: " + Util.encodeBase64(hash));
    }
}
