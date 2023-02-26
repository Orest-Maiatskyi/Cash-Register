package services_tests.validation_tests;

import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.validators.AddGoodToOrderValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddGoodToOrderValidatorTest {

    @Test
    public void testWithValidData() throws ValidatorException {

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(0);
        orderBean.setGoodCode("00000000");
        orderBean.setQuantity(10.0f);

        assertNotNull(AddGoodToOrderValidator.validate(orderBean));
    }

    @Test
    public void testWithInvalidOrderId() {

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(-1);
        orderBean.setGoodCode("00000000");
        orderBean.setQuantity(10.0f);

        assertThrows(ValidatorException.class, () -> AddGoodToOrderValidator.validate(orderBean));
    }

    @Test
    public void testWithInvalidGoodCode() {

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(0);
        orderBean.setGoodCode("001");
        orderBean.setQuantity(10.0f);

        assertThrows(ValidatorException.class, () -> AddGoodToOrderValidator.validate(orderBean));
    }

    @Test
    public void testWithInvalidQuantity() {

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(0);
        orderBean.setGoodCode("00000000");
        orderBean.setQuantity(0.0f);

        assertThrows(ValidatorException.class, () -> AddGoodToOrderValidator.validate(orderBean));
    }

}
