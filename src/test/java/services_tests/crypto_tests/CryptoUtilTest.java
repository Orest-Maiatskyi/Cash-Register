package services_tests.crypto_tests;

import com.epam.cashregister.services.crypto.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CryptoUtilTest {

    @Test
    public void testGenerate64BitSalt() {
        byte[] salt = Util.generate64BitSalt();
        assertNotNull(salt);
        assertTrue(salt.length == 8);
    }

    @Test
    public void testEncodeDecodeBase64() {
        byte[] data = new byte[] {1, 2, 3};
        String encoded = Util.encodeBase64(data);
        assertNotNull(encoded);

        byte[] decoded = Util.decodeBase64(encoded);
        assertArrayEquals(data, decoded);
    }

    @Test
    public void testEncodeDecodeBase64WithoutPadding() {
        byte[] data = new byte[] {1, 2, 3};
        String encoded = Util.encodeBase64WithoutPadding(data);
        assertNotNull(encoded);

        byte[] decoded = Util.decodeBase64(encoded);
        assertArrayEquals(data, decoded);
    }


}
