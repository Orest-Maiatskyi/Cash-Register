package services_tests.validation_tests;

import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.validators.GoodsPaginationValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoodsPaginationValidatorTest {

    @Test
    public void testWithValidData() {
        assertDoesNotThrow(() -> GoodsPaginationValidator.validate("%data%", "goods.id", 20, 30));
    }

    @Test
    public void testWithInvalidLikeData() {
        assertThrows(ValidatorException.class, () -> GoodsPaginationValidator.validate(null, "goods.id", 20, 30));
    }

    @Test
    public void testWithInvalidOrderBy() {
        assertThrows(ValidatorException.class, () -> GoodsPaginationValidator.validate("%data%", "goods.i", 20, 30));
    }

    @Test
    public void testWithInvalidOffset() {
        assertThrows(ValidatorException.class, () -> GoodsPaginationValidator.validate("%data%", "goods.id", -1, 30));
    }

    @Test
    public void testWithInvalidRowCount() {
        assertThrows(ValidatorException.class, () -> GoodsPaginationValidator.validate("%data%", "goods.id", 20, -1));
    }

}
