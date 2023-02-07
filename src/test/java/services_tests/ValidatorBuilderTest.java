package services_tests;

import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.Validator;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorBuilderTest {
    private final Validator validator = new ValidatorBuilder();

    @Test
    public void testValidateGoodCode_withValidCode() throws ValidatorException {
        validator.validateGoodCode("12345678");
    }

    @Test
    public void testValidateGoodCode_withNullCode() {
        assertThrows(ValidatorException.class, () -> validator.validateGoodCode(null));
    }

    @Test
    public void testValidateGoodCode_withInvalidLengthCode() {
        assertThrows(ValidatorException.class, () -> validator.validateGoodCode("123456"));
    }

    @Test
    public void testValidateGoodCode_withInvalidFormatCode() {
        assertThrows(ValidatorException.class, () -> validator.validateGoodCode("abcdefgh"));
    }

    @Test
    public void testValidateGoodTitle_withValidTitle() throws ValidatorException {
        validator.validateGoodTitle("Valid Title");
    }

    @Test
    public void testValidateGoodTitle_withNullTitle() {
        assertThrows(ValidatorException.class, () -> validator.validateGoodTitle(null));
    }

    @Test
    public void testValidateGoodTitle_withInvalidLengthTitle() {
        assertThrows(ValidatorException.class, () -> validator.validateGoodTitle("V T"));
    }

    @Test
    public void testValidateGoodTitle_withInvalidFormatTitle() {
        assertThrows(ValidatorException.class, () -> validator.validateGoodTitle("Invalid  Title"));
    }

    // Test for validateGoodDescription and validateGoodMeasurement methods
    // could be similar to the tests for validateGoodTitle method.

    @Test
    public void testValidateGoodMeasurementId_withValidMeasurementId() throws ValidatorException {
        validator.validateGoodMeasurementId(1);
    }

    @Test
    public void testValidateGoodMeasurementId_withInvalidMeasurementId() {
        assertThrows(ValidatorException.class, () -> validator.validateGoodMeasurementId(-1));
    }

    @Test
    public void testValidateGoodPrice_withValidPrice() throws ValidatorException {
        validator.validateGoodPrice(10.0f);
    }

    @Test
    public void testValidateGoodPrice_withInvalidPrice() {
        assertThrows(ValidatorException.class, () -> validator.validateGoodPrice(-10.0f));
    }

    @Test
    public void testValidateGoodQuantity_withValidQuantity() throws ValidatorException {
        validator.validateGoodQuantity(10.0f);
    }

    @Test
    public void testValidateGoodQuantity_withInvalidQuantity() {
        assertThrows(ValidatorException.class, () -> validator.validateGoodQuantity(-10.0f));
    }

    @Test
    public void validateGoodMeasurement_ValidInput_ReturnsValidatorBuilder() throws ValidatorException {
        // Given
        String goodMeasurement = "PC";
        ValidatorBuilder validatorBuilder = new ValidatorBuilder();

        // When
        Validator result = validatorBuilder.validateGoodMeasurement(goodMeasurement);

        // Then
        assertEquals(validatorBuilder, result);
    }


    @Test
    public void validateGoodMeasurementId_ValidInput_ReturnsValidatorBuilder() throws ValidatorException {
        // Given
        int goodMeasurementId = 1;
        ValidatorBuilder validatorBuilder = new ValidatorBuilder();

        // When
        Validator result = validatorBuilder.validateGoodMeasurementId(goodMeasurementId);

        // Then
        assertEquals(validatorBuilder, result);
    }

    @Test
    public void validateGoodPrice_ValidInput_ReturnsValidatorBuilder() throws ValidatorException {
        // Given
        float goodPrice = 10.0f;
        ValidatorBuilder validatorBuilder = new ValidatorBuilder();

        // When
        Validator result = validatorBuilder.validateGoodPrice(goodPrice);

        // Then
        assertEquals(validatorBuilder, result);
    }

    @Test
    public void validateGoodQuantity_ValidInput_ReturnsValidatorBuilder() throws ValidatorException {
        // Given
        float goodQuantity = 10.0f;
        ValidatorBuilder validatorBuilder = new ValidatorBuilder();

        // When
        Validator result = validatorBuilder.validateGoodQuantity(goodQuantity);

        // Then
        assertEquals(validatorBuilder, result);
    }

    @Test
    public void testValidateOrderId_ValidId() {
        int orderId = 1;
        ValidatorBuilder validatorBuilder = new ValidatorBuilder();
        try {
            validatorBuilder.validateOrderId(orderId);
        } catch (ValidatorException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testValidateLoginEmail_NullEmail() {
        String loginEmail = null;
        ValidatorBuilder validatorBuilder = new ValidatorBuilder();
        try {
            validatorBuilder.validateLoginEmail(loginEmail);
            fail("Expected a ValidatorException to be thrown.");
        } catch (ValidatorException e) {
            assertEquals("Invalid login email.", e.getMessage());
        }
    }

    @Test
    public void testValidateLoginEmail_EmptyEmail() {
        String loginEmail = "";
        ValidatorBuilder validatorBuilder = new ValidatorBuilder();
        try {
            validatorBuilder.validateLoginEmail(loginEmail);
            fail("Expected a ValidatorException to be thrown.");
        } catch (ValidatorException e) {
            assertEquals("Invalid login email format (the email mustn't be empty).", e.getMessage());
        }
    }

    @Test
    public void testValidateLoginEmail_ValidEmail() {
        String loginEmail = "email@example.com";
        ValidatorBuilder validatorBuilder = new ValidatorBuilder();
        try {
            validatorBuilder.validateLoginEmail(loginEmail);
        } catch (ValidatorException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

}
