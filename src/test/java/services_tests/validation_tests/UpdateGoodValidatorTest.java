package services_tests.validation_tests;

import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.validators.UpdateGoodValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateGoodValidatorTest {

    @Test
    public void testWithValidData() throws ValidatorException {

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");
        goodBean.setTitle("title");
        goodBean.setDescription("description");
        goodBean.setPrice(10.0f);

        assertNotNull(UpdateGoodValidator.validate(goodBean));
    }

    @Test
    public void testWithInvalidGoodCode() {

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("0000001");
        goodBean.setTitle("title");
        goodBean.setDescription("description");
        goodBean.setPrice(10.0f);

        assertThrows(ValidatorException.class, () -> UpdateGoodValidator.validate(goodBean));
    }

    @Test
    public void testWithInvalidGoodTitle() {

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");
        goodBean.setTitle(null);
        goodBean.setDescription("description");
        goodBean.setPrice(10.0f);

        assertThrows(ValidatorException.class, () -> UpdateGoodValidator.validate(goodBean));
    }

    @Test
    public void testWithInvalidGoodDescription() {

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");
        goodBean.setTitle("title");
        goodBean.setDescription(null);
        goodBean.setPrice(10.0f);

        assertThrows(ValidatorException.class, () -> UpdateGoodValidator.validate(goodBean));
    }

    @Test
    public void testWithInvalidPrice() {

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");
        goodBean.setTitle("title");
        goodBean.setDescription("description");
        goodBean.setPrice(0.0f);

        assertThrows(ValidatorException.class, () -> UpdateGoodValidator.validate(goodBean));
    }

}
