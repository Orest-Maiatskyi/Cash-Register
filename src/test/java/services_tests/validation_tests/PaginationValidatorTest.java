package services_tests.validation_tests;

import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.validators.PaginationValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaginationValidatorTest {

    @Test
    public void testWithValidData() {
        assertDoesNotThrow(() -> PaginationValidator.validate("%data%", "goods.id", 20, 30));
    }

    @Test
    public void testWithInvalidLikeData() {
        assertThrows(ValidatorException.class, () -> PaginationValidator.validate(null, "goods.id", 20, 30));
    }

    @Test
    public void testWithInvalidOrderBy() {
        assertThrows(ValidatorException.class, () -> PaginationValidator.validate("%data%", "goods.i", 20, 30));
    }

    @Test
    public void testWithInvalidOffset() {
        assertThrows(ValidatorException.class, () -> PaginationValidator.validate("%data%", "goods.id", -1, 30));
    }

    @Test
    public void testWithInvalidRowCount() {
        assertThrows(ValidatorException.class, () -> PaginationValidator.validate("%data%", "goods.id", 20, -1));
    }

}
