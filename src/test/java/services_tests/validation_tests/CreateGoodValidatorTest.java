package services_tests.validation_tests;

import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.MeasurementBean;
import com.epam.cashregister.entities.UserBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.validators.AddUserValidator;
import com.epam.cashregister.services.validateservice.validators.CreateGoodValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateGoodValidatorTest {

    @Test
    public void testWithValidData() throws ValidatorException {

        MeasurementBean measurementBean = new MeasurementBean();
        measurementBean.setId(1);

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");
        goodBean.setTitle("Title");
        goodBean.setDescription("Description");
        goodBean.setPrice(10.0f);
        goodBean.setMeasurement(measurementBean);

        assertNotNull(CreateGoodValidator.validate(goodBean));
    }

    @Test
    public void testWithInvalidMeasurementId() {

        MeasurementBean measurementBean = new MeasurementBean();
        measurementBean.setId(-1);

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");
        goodBean.setTitle("Title");
        goodBean.setDescription("Description");
        goodBean.setPrice(10.0f);
        goodBean.setMeasurement(measurementBean);

        assertThrows(ValidatorException.class, () -> CreateGoodValidator.validate(goodBean));
    }

    @Test
    public void testWithInvalidGoodCode() {

        MeasurementBean measurementBean = new MeasurementBean();
        measurementBean.setId(1);

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("0000001");
        goodBean.setTitle("Title");
        goodBean.setDescription("Description");
        goodBean.setPrice(10.0f);
        goodBean.setMeasurement(measurementBean);

        assertThrows(ValidatorException.class, () -> CreateGoodValidator.validate(goodBean));
    }

    @Test
    public void testWithInvalidGoodTitle() {

        MeasurementBean measurementBean = new MeasurementBean();
        measurementBean.setId(1);

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");
        goodBean.setTitle("");
        goodBean.setDescription("Description");
        goodBean.setPrice(10.0f);
        goodBean.setMeasurement(measurementBean);

        assertThrows(ValidatorException.class, () -> CreateGoodValidator.validate(goodBean));
    }

    @Test
    public void testWithInvalidGoodDescription() {

        MeasurementBean measurementBean = new MeasurementBean();
        measurementBean.setId(1);

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");
        goodBean.setTitle("Title");
        goodBean.setDescription("");
        goodBean.setPrice(10.0f);
        goodBean.setMeasurement(measurementBean);

        assertThrows(ValidatorException.class, () -> CreateGoodValidator.validate(goodBean));
    }

    @Test
    public void testWithInvalidGoodPrice() {

        MeasurementBean measurementBean = new MeasurementBean();
        measurementBean.setId(1);

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");
        goodBean.setTitle("Title");
        goodBean.setDescription("Description");
        goodBean.setPrice(0.0f);
        goodBean.setMeasurement(measurementBean);

        assertThrows(ValidatorException.class, () -> CreateGoodValidator.validate(goodBean));
    }

}
