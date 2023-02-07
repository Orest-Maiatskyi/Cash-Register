package com.epam.cashregister.services.validateservice.validators;

import com.epam.cashregister.entities.WarehouseBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.Validator;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;

public class WriteOffGoodValidator {

    public static Validator validate(final WarehouseBean warehouseBean) throws ValidatorException {
        Validator validator = new ValidatorBuilder();

        validator
                .validateGoodCode(warehouseBean.getGoodBean().getCode())
                .validateStorageId(warehouseBean.getStorageBean().getId())
                .validateGoodQuantity(warehouseBean.getQuantity());

        return validator;
    }

}
