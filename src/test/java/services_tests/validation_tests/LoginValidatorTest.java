package services_tests.validation_tests;

import com.epam.cashregister.entities.LoginBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.validators.LoginValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginValidatorTest {

    @Test
    public void testWithValidData() throws ValidatorException {

        LoginBean loginBean = new LoginBean();
        loginBean.setEmail("email@gmail.com");
        loginBean.setPassword("password");

        assertNotNull(LoginValidator.validate(loginBean));
    }

    @Test
    public void testWithInvalidEmail() {

        LoginBean loginBean = new LoginBean();
        loginBean.setEmail("@gmail.com");
        loginBean.setPassword("password");

        assertThrows(ValidatorException.class, () -> LoginValidator.validate(loginBean));
    }

    @Test
    public void testWithInvalidPassword() {

        LoginBean loginBean = new LoginBean();
        loginBean.setEmail("email@gmail.com");
        loginBean.setPassword("");

        assertThrows(ValidatorException.class, () -> LoginValidator.validate(loginBean));
    }

}
