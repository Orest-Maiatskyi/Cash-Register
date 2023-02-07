package services_tests.crypto_tests;

import com.epam.cashregister.services.crypto.PBKDF2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class PBKDF2Test {

    @Test
    public void testGenerateDefaultHash() throws Exception {
        String data = "data";
        byte[] salt = "salt".getBytes();
        byte[] expected = PBKDF2.generateDefaultHash(data, salt);
        byte[] actual = PBKDF2.generateDefaultHash(data, salt);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGenerateCustomHash() throws Exception {
        String data = "data";
        byte[] salt = "salt".getBytes();
        int iterationCount = 10000;
        int keyLength = 256;
        byte[] expected = PBKDF2.generateCustomHash(data, salt, iterationCount, keyLength);
        byte[] actual = PBKDF2.generateCustomHash(data, salt, iterationCount, keyLength);
        assertArrayEquals(expected, actual);
    }
}
