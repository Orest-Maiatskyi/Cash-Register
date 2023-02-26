package services_tests.validation_tests;

import com.epam.cashregister.entities.UserBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.validators.AddUserValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddUserValidatorTest {

    @Test
    public void testWithValidData() throws ValidatorException {

        UserBean userBean = new UserBean();
        userBean.setRoleId(1);
        userBean.setFirstName("First");
        userBean.setLastName("Last");
        userBean.setEmail("email@gmail.com");
        userBean.setPassword_1("12345");
        userBean.setPassword_2("12345");

        assertNotNull(AddUserValidator.validate(userBean));
    }

    @Test
    public void testWithInvalidRoleId() {

        UserBean userBean = new UserBean();
        userBean.setRoleId(-1);
        userBean.setFirstName("First");
        userBean.setLastName("Last");
        userBean.setEmail("email@gmail.com");
        userBean.setPassword_1("12345");
        userBean.setPassword_2("12345");

        assertThrows(ValidatorException.class, () -> AddUserValidator.validate(userBean));
    }

    @Test
    public void testWithInvalidFirstName() {

        UserBean userBean = new UserBean();
        userBean.setRoleId(1);
        userBean.setFirstName("");
        userBean.setLastName("Last");
        userBean.setEmail("email@gmail.com");
        userBean.setPassword_1("12345");
        userBean.setPassword_2("12345");

        assertThrows(ValidatorException.class, () -> AddUserValidator.validate(userBean));
    }

    @Test
    public void testWithInvalidLastName() {

        UserBean userBean = new UserBean();
        userBean.setRoleId(1);
        userBean.setFirstName("First");
        userBean.setLastName("");
        userBean.setEmail("email@gmail.com");
        userBean.setPassword_1("12345");
        userBean.setPassword_2("12345");

        assertThrows(ValidatorException.class, () -> AddUserValidator.validate(userBean));
    }

    @Test
    public void testWithInvalidEmail() {

        UserBean userBean = new UserBean();
        userBean.setRoleId(1);
        userBean.setFirstName("First");
        userBean.setLastName("Last");
        userBean.setEmail("email@");
        userBean.setPassword_1("12345");
        userBean.setPassword_2("12345");

        assertThrows(ValidatorException.class, () -> AddUserValidator.validate(userBean));
    }

    @Test
    public void testWithNonEqualsPassword() {

        UserBean userBean = new UserBean();
        userBean.setRoleId(1);
        userBean.setFirstName("First");
        userBean.setLastName("Last");
        userBean.setEmail("email@gmail.com");
        userBean.setPassword_1("1234");
        userBean.setPassword_2("12345");

        assertThrows(ValidatorException.class, () -> AddUserValidator.validate(userBean));
    }

}
