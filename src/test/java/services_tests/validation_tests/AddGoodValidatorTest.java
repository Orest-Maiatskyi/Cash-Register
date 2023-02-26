package services_tests.validation_tests;

import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.entities.WarehouseBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.validators.AddGoodValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddGoodValidatorTest {

    @Test
    public void testWithValidData() throws ValidatorException {

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");

        StorageBean storageBean = new StorageBean();
        storageBean.setId(1);

        WarehouseBean warehouseBean = new WarehouseBean();
        warehouseBean.setGood(goodBean);
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setQuantity(10.0f);

        assertNotNull(AddGoodValidator.validate(warehouseBean));
    }

    @Test
    public void testWithInvalidGoodCode() {

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00001");

        StorageBean storageBean = new StorageBean();
        storageBean.setId(1);

        WarehouseBean warehouseBean = new WarehouseBean();
        warehouseBean.setGood(goodBean);
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setQuantity(10.0f);

        assertThrows(ValidatorException.class, () -> AddGoodValidator.validate(warehouseBean));
    }

    @Test
    public void testWithInvalidStorageId() {

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");

        StorageBean storageBean = new StorageBean();
        storageBean.setId(-1);

        WarehouseBean warehouseBean = new WarehouseBean();
        warehouseBean.setGood(goodBean);
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setQuantity(10.0f);

        assertThrows(ValidatorException.class, () -> AddGoodValidator.validate(warehouseBean));
    }

    @Test
    public void testWithInvalidQuantity() {

        GoodBean goodBean = new GoodBean();
        goodBean.setCode("00000001");

        StorageBean storageBean = new StorageBean();
        storageBean.setId(1);

        WarehouseBean warehouseBean = new WarehouseBean();
        warehouseBean.setGood(goodBean);
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setQuantity(0.0f);

        assertThrows(ValidatorException.class, () -> AddGoodValidator.validate(warehouseBean));
    }

}
